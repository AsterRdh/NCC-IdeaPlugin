package space.cyberaster.common.ncc.plugin.NCCPlugin.frame;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;
import space.cyberaster.common.ncc.plugin.NCCPlugin.base.NotificationGroups;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.actions.CopyResBean;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.CopyConUtils;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.FileUtils;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class SetModuleFrame extends DialogWrapper {

    private Project project;
    private JTextField moduleBox = new JTextField(35);
    private VirtualFile[] selectFiles;
    private Consumer<CopyResBean> callBack;

    public SetModuleFrame(@Nullable Project project, VirtualFile[] selectFiles, Consumer<CopyResBean> callBack) {
        super(project);
        this.project = project;
        this.selectFiles = selectFiles;
        this.callBack = callBack;
        init();
        setTitle("复制到");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        JLabel jLabel = new JLabel("模块名称");
        panel.add(jLabel);
        panel.add(moduleBox);

        return panel;
    }

    @Override
    protected void doOKAction() {
        String moduleName = moduleBox.getText();
        CopyConUtils copyConUtils = new CopyConUtils();
        List<VirtualFile> okFiles = copyConUtils.getConFile(selectFiles);
        List<VirtualFile> fFiles = FileUtils.getOnlyFile(okFiles);
        String nccHome = BaseSetting.getInstance(project).getNccHome();
        CopyResBean resBean = copyConUtils.copyToNCCHome(nccHome,fFiles,moduleName);

        super.doOKAction();
        callBack.accept(resBean);
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        return super.doValidate();
    }
}
