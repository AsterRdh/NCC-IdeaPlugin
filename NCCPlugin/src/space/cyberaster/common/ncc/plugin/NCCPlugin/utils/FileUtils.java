package space.cyberaster.common.ncc.plugin.NCCPlugin.utils;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileUtils {

    public static  List<VirtualFile> getOnlyFile( List<VirtualFile> okFiles){
        return okFiles.stream().filter(path -> {
            //过滤重复关系
            boolean isTopPath=true;
            for (VirtualFile file : okFiles) {
                if (!file.getPath().equals(path.getPath()) && Pattern.matches(file.getPath() +(file.isDirectory()? "/.*":".*"),path.getPath())){
                    isTopPath = false;
                    break;
                }
            }
            return isTopPath;
        }).collect(Collectors.toList());
    }

    public static String getModuleName( File fileModuleXML) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileModuleXML);
            return document.getDocumentElement().getAttributeNode("name").getValue();
        } catch (IOException | ParserConfigurationException | SAXException ioException) {
            ioException.printStackTrace();
        }
        return "";
    }

    public static String getFileExtension(DataContext dataContext) {
        VirtualFile file = LangDataKeys.VIRTUAL_FILE.getData(dataContext);
        return file == null ? null : file.getExtension();
    }

    public static void copyFile(String fromPath,String toPath) throws IOException{
        copyFile(new File(fromPath),new File(toPath));
    }

    public static void copyFile(File from, File to) throws IOException {


    }
    
    public static File[] getAllFiles(VirtualFile[] virtualFiles,String ... filerFileType){
        List<File> files = new ArrayList<>();
        Arrays.stream(virtualFiles)
            .filter(path -> {
                //过滤重复关系
                boolean isTopPath = true;
                for (VirtualFile file : virtualFiles) {
                    if (!file.getPath().equals(path.getPath()) && Pattern.matches(file.getPath() + (file.isDirectory() ? "/.*" : ".*"), path.getPath())) {
                        isTopPath = false;
                        break;
                    }
                }
                return isTopPath;
            })
            .forEach(virtualFile -> {
                files.add(new File(virtualFile.getPath()));
            });

        List<File> allFiles = getFileList(files.toArray(new File[0]),filerFileType);

        
        return allFiles.toArray(new File[0]);

    }
    
    private static List<File> getFileList(File[] files,String ... filerFileType){
        List<File> allFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()){
                allFiles.addAll(getFileList(file.listFiles()));
            }else {
                //Pattern.matches(".*\\.META-INF.*",fFile.getPath())
                for (String type : filerFileType) {
                    if (Pattern.matches(type,file.getName())){
                        allFiles.add(file);
                    }
                }


            }
        }
        return allFiles;
    }



}
