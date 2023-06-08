package space.cyberaster.common.ncc.plugin.NCCPlugin.utils;

import b.a.S;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.actions.CopyResBean;
import space.cyberaster.common.ncc.plugin.NCCPlugin.enums.FileType;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class CopyConUtils {
    public CopyResBean copyToNCCHome(String nccHome, List<VirtualFile> fFiles,String moduleName){
        CopyResBean resBean = new CopyResBean();
        String umpPath = nccHome+"/modules/";
        String yyPath = nccHome+"/hotwebs/nccloud/WEB-INF/extend/yyconfig/";
        String moduleNameP = StringUtils.isBlank(moduleName) ? null:moduleName;
        //分出upm和yyConfig
        for (VirtualFile fFile : fFiles) {
            File orcFile = new File(fFile.getPath());
            File toFile = null;
            if (Pattern.matches(".*/META-INF.*",fFile.getPath())){
                //upm文件
                File fileModuleXML = null;
                String pathModule = null;
                if (fFile.isDirectory()) {
                    pathModule = fFile.getPath() + "/module.xml";
                    fileModuleXML = new File(pathModule);
                    if (!fileModuleXML.exists()) {
                        pathModule = fFile.getParent().getParent().getPath() + "/META-INF/module.xml";
                        fileModuleXML = new File(pathModule);
                    }
                } else {
                    if (fFile.getName().equals("module.xml")) {
                        pathModule = fFile.getPath();
                    } else {
                        pathModule = fFile.getParent().getParent().getParent().getPath() + "/META-INF/module.xml";
                    }
                    fileModuleXML = new File(pathModule);
                }

                if (moduleNameP == null) {
                    moduleNameP = space.cyberaster.common.ncc.plugin.NCCPlugin.utils.FileUtils.getModuleName(fileModuleXML);
                }

                umpPath = umpPath + moduleNameP.toLowerCase() + "/META-INF/";
                createModuleXml(umpPath,moduleNameP);

                if (!fFile.isDirectory()) umpPath = umpPath + fFile.getName();
                toFile = new File(umpPath);

                pushAllFile(orcFile,(file -> resBean.addUPMFileName(file)));
            }else {
                //yyConfig文件
                String subPath = orcFile.getAbsolutePath().replaceAll(".*\\\\yyconfig", "");
                toFile = new File(yyPath+subPath);
                pushAllFile(orcFile,(file -> resBean.addYYFileName(file)));
            }


            try {
                if (orcFile.isDirectory()){
                    org.apache.commons.io.FileUtils.copyDirectory(orcFile,toFile);
                }else {
                    FileUtils.copyFile(orcFile,toFile);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return resBean;
    }



    private void createModuleXml(String metaInfoPath,String module){
        File xmlFile = new File(metaInfoPath+"/module.xml");
        if (!xmlFile.exists()){
            try {
                String moduleXML =
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<module name=\""+module+"\">\n" +
                                "    <public>\n" +
                                "    </public>\n" +
                                "    <private>\n" +
                                "    </private>\n" +
                                "</module>";
                FileUtils.write(xmlFile,moduleXML,"utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<VirtualFile> getConFile(VirtualFile[] selectFiles){
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
        return okFiles;
    }


    private static void pushAllFile(File file, Consumer<File> addFun){
        if (file.isDirectory()){
            for (File listFile : file.listFiles()) {
                pushAllFile(listFile,addFun);
            }
        }else {
            addFun.accept(file);
        }

    }
}
