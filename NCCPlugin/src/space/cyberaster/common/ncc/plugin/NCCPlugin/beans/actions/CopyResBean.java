package space.cyberaster.common.ncc.plugin.NCCPlugin.beans.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CopyResBean {
    List<String> upmNames ;
    List<String> yyNames ;

    public CopyResBean() {
        upmNames = new ArrayList<>();
        yyNames = new ArrayList<>();

    }

    public void addYYFileName(File file){
        yyNames.add(file.getName());
    }

    public void addUPMFileName(File file){
        upmNames.add(file.getName());
    }

    @Override
    public String toString() {
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

        return content.toString();
    }
}
