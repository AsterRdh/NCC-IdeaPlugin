package space.cyberaster.common.ncc.plugin.NCCPlugin.utils;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MyFileChooserDescriptor extends FileChooserDescriptor {

    Set<String> showFileTypes = new HashSet<>();

    public MyFileChooserDescriptor(boolean chooseFiles, boolean chooseFolders, boolean chooseJars, boolean chooseJarsAsFiles, boolean chooseJarContents, boolean chooseMultiple) {
        super(chooseFiles, chooseFolders, chooseJars, chooseJarsAsFiles, chooseJarContents, chooseMultiple);
    }

    public void pushShowFileType(String ... types){
        if (types==null || types.length==0) return;
        for (String type : types) {
            showFileTypes.add(type.toUpperCase());
        }
    }

    @Override
    public boolean isFileVisible(VirtualFile file, boolean showHiddenFiles) {
        boolean fileVisible = super.isFileVisible(file, showHiddenFiles);
        if(fileVisible) {
            if (!file.isDirectory()){
                String type = file.getExtension().toUpperCase();
                fileVisible = showFileTypes.stream().filter(i->i.equals(type)).findFirst().isPresent();
            }
        }
        return fileVisible;
    }
}
