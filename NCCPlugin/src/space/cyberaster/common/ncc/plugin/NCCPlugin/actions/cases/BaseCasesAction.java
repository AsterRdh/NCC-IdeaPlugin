package space.cyberaster.common.ncc.plugin.NCCPlugin.actions.cases;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class BaseCasesAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent event) {
        super.update(event);
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();
        event.getPresentation().setVisible(start < end);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        String selectedText = primaryCaret.getSelectedText();
        String text = trance(selectedText);
        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {
                editor.getDocument().replaceString(primaryCaret.getSelectionStart(),primaryCaret.getSelectionEnd(),text);
            }
        });
    }

    private String trance(String string){
        String[] split = string.split("\n");
        Iterator<String> iterator = Arrays.stream(split).iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (iterator.hasNext()){
            String next = iterator.next();
            stringBuffer = stringBuffer.append(tranceText(next));
            if (iterator.hasNext()) stringBuffer = stringBuffer.append("\n");
        }
        return stringBuffer.toString();

    }

    protected abstract String tranceText(String string);


    protected List<String> split(String string){
        List<String> stringList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = string.toCharArray();
        int firstIndex = -1;
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (firstIndex < 0){
                if (Character.isLetter(aChar)){
                    firstIndex = i;
                    stringList.add(stringBuffer.toString());
                    stringBuffer = new StringBuffer().append(aChar);
                }else {
                    stringBuffer = stringBuffer.append(aChar);
                }

            }else {
                if (!Character.isLetter(aChar)) continue;
                char fontChar = chars[i - 1];
                if (Character.isLetter(fontChar)){
                    if ((Character.isUpperCase(fontChar) && Character.isUpperCase(aChar))
                            || ( Character.isLowerCase(fontChar) && Character.isLowerCase(aChar))
                            || ( Character.isUpperCase(fontChar) && Character.isLowerCase(aChar))
                    ){
                        stringBuffer = stringBuffer.append(aChar);
                        if (i+1>=chars.length){
                            stringList.add(stringBuffer.toString());
                        }
                    }else {
                        stringList.add(stringBuffer.toString());
                        stringBuffer = new StringBuffer().append(aChar);
                    }
                }else {
                    stringList.add(stringBuffer.toString());
                    stringBuffer = new StringBuffer().append(aChar);
                }
            }

        }
        return stringList;
    }

}
