package space.cyberaster.common.ncc.plugin.NCCPlugin.actions.cases;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PascalCaseAction extends BaseCasesAction {

    @Override
    protected String tranceText(String string) {
        //pk_salarytypeitem
        List<String> split = split(string);


        StringBuffer stringBuffer =new StringBuffer();
        for (int i = 1; i < split.size(); i++) {
            String first = split.get(i).substring(0, 1).toUpperCase();
            String last = split.get(i).substring(1).toLowerCase();
            stringBuffer = stringBuffer.append(first).append(last);
        }
        return stringBuffer.toString();

    }
}
