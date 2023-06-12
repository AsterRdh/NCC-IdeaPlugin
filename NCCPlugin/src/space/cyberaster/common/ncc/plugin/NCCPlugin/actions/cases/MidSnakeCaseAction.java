package space.cyberaster.common.ncc.plugin.NCCPlugin.actions.cases;


import java.util.Iterator;
import java.util.List;

public class MidSnakeCaseAction extends BaseCasesAction {

    @Override
    protected String tranceText(String string) {
        List<String> split = split(string);


        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> iterator = split.iterator();
        stringBuffer = stringBuffer.append(iterator.next());
        while (iterator.hasNext()){
            stringBuffer = stringBuffer.append(iterator.next().toLowerCase());
            if (iterator.hasNext()) stringBuffer = stringBuffer.append("-");
        }

        return stringBuffer.toString();
    }

    private boolean isUpper(char c){
        return Character.isUpperCase(c);
    }
}
