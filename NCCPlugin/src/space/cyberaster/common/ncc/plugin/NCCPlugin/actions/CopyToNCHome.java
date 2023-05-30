package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import b.a.F;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import space.cyberaster.common.ncc.plugin.NCCPlugin.enums.FileType;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CopyToNCHome extends AnAction {
    private  VirtualFile[] selectFiles;


    @Override
    public void update(AnActionEvent event) {//根据扩展名是否是支持，显示隐藏此Action
        super.update(event);
        selectFiles = LangDataKeys.VIRTUAL_FILE_ARRAY.getData(event.getDataContext());
        boolean okFile = false;
        for (VirtualFile file : selectFiles) {
            if (file != null){
                FileType type = FileType.getFileType(file);
                switch (type){
                    case UNKNOWN:
                        okFile = false;
                        break;
                    case DIRECTORY:
                        String path = file.getPath();
                        okFile = Pattern.matches(".*/yyconfig",path)
                                || Pattern.matches(".*/yyconfig/.*",path)
                                || file.getName().equals("META-INF");
                        break;
                    default:
                        okFile = true;
                }
            }
            if (okFile) break;
        }


        event.getPresentation().setVisible(okFile);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        List<VirtualFile> okFiles=new ArrayList<>();
        for (VirtualFile file : selectFiles) {
            if (file != null){
                FileType type = FileType.getFileType(file);
                switch (type){
                    case UNKNOWN:
                        break;
                    case DIRECTORY:
                        String path = file.getPath();
                        if (Pattern.matches(".*/yyconfig",path)
                            || Pattern.matches(".*/yyconfig/.*",path)
                            || file.getName().equals("META-INF")){
                            okFiles.add(file);
                        }
                        break;
                    default:
                        okFiles.add(file);
                }
            }
        }

        List<VirtualFile> fFiles = okFiles.stream().filter(path -> {
            //过滤重复关系
            boolean isTopPath=true;
            for (VirtualFile file : okFiles) {
                if (!file.getPath().equals(path.getPath()) && Pattern.matches(file.getPath() +(file.isDirectory()? "/.*":".*"),path.getPath())){
                    isTopPath = false;
                    break;
                }
            }

            return isTopPath;
        })
        .collect(Collectors.toList());



        //复制到home
        //获取Home
        String nccHome = BaseSetting.getInstance(e.getProject()).getNccHome();
        String umpPath = nccHome+"/modules/";
        String yyPath = nccHome+"/hotwebs/nccloud/WEB-INF/extend/yyconfig/";
        List<String> upmNames = new ArrayList<>();
        List<String> yyNames = new ArrayList<>();

        //分出upm和yyConfig
        for (VirtualFile fFile : fFiles) {
            File orcFile = new File(fFile.getPath());
            File toFile = null;
            if (Pattern.matches(".*/META-INF.*",fFile.getPath())){
                //upm文件
                File fileModuleXML = null;
                String pathModule = null;
                String moduleName = "";
                if (fFile.isDirectory()){
                    pathModule = fFile.getPath() + "/module.xml";
                    fileModuleXML=new File(pathModule);
                    if (!fileModuleXML.exists()){
                        pathModule = fFile.getParent().getParent().getPath() + "/META-INF/module.xml";
                        fileModuleXML=new File(pathModule);
                    }
                }else {
                    if (fFile.getName().equals("module.xml")){
                        pathModule = fFile.getPath();
                    }else {
                        pathModule = fFile.getParent().getParent().getParent().getPath() + "/META-INF/module.xml";
                    }
                    fileModuleXML = new File(pathModule);
                }
                try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document document = db.parse(fileModuleXML);
                    moduleName = document.getDocumentElement().getAttributeNode("name").getValue();
                    umpPath = umpPath+moduleName+"/META-INF/";
                    if (!fFile.isDirectory()) umpPath = umpPath+fFile.getName();
                    toFile = new File(umpPath);

                } catch (IOException | ParserConfigurationException | SAXException ioException) {
                    ioException.printStackTrace();
                }
                pushAllFile(orcFile,upmNames);
            }else {
                //yyConfig文件
                String subPath = orcFile.getAbsolutePath().replaceAll(".*\\\\yyconfig", "");
                toFile = new File(yyPath+subPath);
                pushAllFile(orcFile,yyNames);
            }


            try {
                if (orcFile.isDirectory()){
                    FileUtils.copyDirectory(orcFile,toFile);
                }else {
                    FileUtils.copyFile(orcFile,toFile);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        }

        StringBuilder content = new StringBuilder();
        if (!upmNames.isEmpty()){
            upmNames.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            content.append("--upm--------------- \r\n");
            upmNames.forEach(name->content.append(name).append(" \r\n"));
        }

        if (!yyNames.isEmpty()){
            yyNames.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

            content.append("--yyConfig---------- \r\n");
            yyNames.forEach(name->content.append(name).append(" \r\n"));
        }


        NotificationGroup notificationGroup = new NotificationGroup("testid", NotificationDisplayType.BALLOON, false);
        /**
         * content :  通知内容
         * type  ：通知的类型，warning,info,error
         */
        Notification notification = notificationGroup.createNotification(
                "复制配置文件到NCC Home",
                "复制成功",
                content.toString(),
                NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    private void pushAllFile(File file,List<String> list){
        if (file.isDirectory()){
            for (File listFile : file.listFiles()) {
                pushAllFile(listFile,list);
            }
        }else {
            list.add(file.getName());
        }

    }


}
