package space.cyberaster.common.ncc.plugin.NCCPlugin.utils;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static String getFileExtension(DataContext dataContext) {
        VirtualFile file = LangDataKeys.VIRTUAL_FILE.getData(dataContext);
        return file == null ? null : file.getExtension();
    }

    public static void copyFile(String fromPath,String toPath) throws IOException{
        copyFile(new File(fromPath),new File(toPath));
    }

    public static void copyFile(File from, File to) throws IOException {


    }



}
