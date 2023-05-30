package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting.NccHomeBean;
import space.cyberaster.common.ncc.plugin.NCCPlugin.frame.SetNCCHomeFrame;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigNCCHomeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        //初始化数据
        BaseSetting setting = BaseSetting.getInstance(e.getProject());
        String nccHome = setting.getNccHome();
        List<NccHomeBean> allNccHomes = setting.getAllNccHomes();
        if (allNccHomes!=null){
            if (nccHome!=null){
                AtomicBoolean hasSelected= new AtomicBoolean(false);
                allNccHomes.stream().forEach(i->{
                    boolean equals = i.getPath().equals(nccHome);
                    i.setSelected(equals);
                    if (equals) hasSelected.set(true);
                });
                if (!hasSelected.get()){
                    setting.setNccHome(null);
                }
            }
        }else {
            allNccHomes = new ArrayList<>();
        }

        SetNCCHomeFrame setNCCHomeFrame = new SetNCCHomeFrame(e.getProject(),allNccHomes );
        setNCCHomeFrame.show();


    }
}
