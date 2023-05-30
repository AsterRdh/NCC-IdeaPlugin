package space.cyberaster.common.ncc.plugin.NCCPlugin.enums;

import com.intellij.openapi.vfs.VirtualFile;
import space.cyberaster.common.ncc.plugin.NCCPlugin.enums.base.IBaseEnum;

import java.util.regex.Pattern;

public enum FileType {
    UPM("upm"),ACTION("xml"),AUTHORIZE("xml"),USER_DEF("xml"),
    DIRECTORY(""),UNKNOWN("*"),YYPATH("/");

    FileType(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public static FileType getFileType(VirtualFile file){

        if (file.isDirectory()){
            return DIRECTORY;
        }else {
            VirtualFile parent = file.getParent();
            String extension = file.getExtension().trim().toLowerCase();
            String pName = parent.getName().toUpperCase();
            if (extension.equals("upm") && pName.equals("META-INF")){
                return UPM;
            }else if (extension.equals("xml")){
                String path = parent.getPath();
                if (Pattern.matches(".*/yyconfig/modules/.*/config/.*",path)){
                    switch (pName){
                        case "ACTION": return ACTION;
                        case "AUTHORIZE": return AUTHORIZE;
                        case "USERDEF": return USER_DEF;
                    }
                }
            }
        }
        return UNKNOWN;
    }


    private String fileExtension;


    public String getFileExtension() {
        return null;
    }
}
