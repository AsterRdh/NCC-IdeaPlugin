package space.cyberaster.common.ncc.plugin.NCCPlugin.frame;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting.NccHomeBean;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.intellij.openapi.ui.ComboBox;

public class SelectNCCHomeFrame extends DialogWrapper {

    private String nccHomePath;
    private Project project;

    public SelectNCCHomeFrame(@Nullable Project project, String nccHomePath) {
        super(project);
        this.nccHomePath = nccHomePath;
        this.project = project;
        init();
        setTitle("设置NCCHome");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        JTextField HomePathTextBox = new JTextField(35);

        List<NccHomeBean> allNccHomes = BaseSetting.getInstance(project).getAllNccHomes();

        List<String> homeNames = allNccHomes.stream().map(i -> i.getName() + "(" + i.getPath() + ")").collect(Collectors.toList());
        ComboBox comboBox= new ComboBox(homeNames.toArray(new String[0]),300);
        comboBox.setSelectedIndex(0);
        comboBox.addItemListener(event->{
            if (event.getStateChange()==1){
                int i = homeNames.indexOf(event.getItem());
                nccHomePath = allNccHomes.get(i).getPath();
            }

        });
        JLabel Label1 = new JLabel("指定NCChome");
        HomePathTextBox.setText(nccHomePath);
        panel.add(Label1);
        panel.add(comboBox);
        return panel;
    }



    @Override
    protected void doOKAction() {
        System.out.println("ok");
        BaseSetting.getInstance(project).setNccHome(nccHomePath);
        super.doOKAction();
    }

    @Override
    public boolean isOK() {

        return super.isOK();
    }
}
