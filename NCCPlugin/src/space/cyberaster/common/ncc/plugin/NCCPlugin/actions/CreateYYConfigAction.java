package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import space.cyberaster.common.ncc.plugin.NCCPlugin.frame.CreateYYConfigFrame;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.FileUtils;

import javax.swing.*;
import java.io.File;

public class CreateYYConfigAction extends AnAction {
    protected VirtualFile selectFile;



    @Override
    public void update(AnActionEvent event) {//根据扩展名是否是支持，显示隐藏此Action
        selectFile = LangDataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        boolean show = selectFile!=null && selectFile.isDirectory() && selectFile.getName().equals("client");
        event.getPresentation().setVisible(show);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        String itemName = selectFile.getParent().getParent().getName().toLowerCase();
        String clientPath = selectFile.getPath();
        //                   client    /src        /SalaryTypeItem/ceri/
        VirtualFile parent = selectFile.getParent().getParent().getParent();

        File file = new File(parent.getPath()+"/META-INF/module.xml");

        String moduleName = FileUtils.getModuleName(file);

        new CreateYYConfigFrame(event.getProject(),clientPath,moduleName,itemName).show();





    }
}
