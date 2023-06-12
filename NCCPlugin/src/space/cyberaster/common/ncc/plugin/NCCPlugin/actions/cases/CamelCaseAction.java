package space.cyberaster.common.ncc.plugin.NCCPlugin.actions.cases;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CamelCaseAction extends BaseCasesAction {

    @Override
    protected String tranceText(String string) {
        List<String> split = split(string);
        StringBuffer stringBuffer =new StringBuffer().append(split.get(0));
        for (int i = 1; i < split.size(); i++) {
            if (i==1){
                stringBuffer = stringBuffer.append(split.get(i).toLowerCase());
            }else {
                String first = split.get(i).substring(0, 1).toUpperCase();
                String last = split.get(i).substring(1).toLowerCase();
                stringBuffer = stringBuffer.append(first).append(last);
            }

        }
        return stringBuffer.toString();
    }

}
