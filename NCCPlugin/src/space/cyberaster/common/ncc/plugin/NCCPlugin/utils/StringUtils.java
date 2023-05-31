package space.cyberaster.common.ncc.plugin.NCCPlugin.utils;

import java.util.Collection;

public class StringUtils {
    public static void appendToStringBuilder(Collection collection,String title,StringBuilder stringBuilder){
        if (!collection.isEmpty()){
            stringBuilder.append("<br>---- ").append(title).append(" ----</br>");
            collection.stream().forEach(i->{
                stringBuilder.append("<br>").append(i).append("</br>");
            });
        }
    }
}
