package space.cyberaster.common.ncc.plugin.NCCPlugin.toolwindow;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileSystemEntry;
import com.intellij.openapi.wm.ToolWindow;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.MyFileChooserDescriptor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class AsterToolWindowContent {

    private ToolWindow toolWindow;
    private Project project;

    public AsterToolWindowContent(ToolWindow toolWindow,Project project) {
        super();
        this.toolWindow = toolWindow ;
        this.project = project;
        init();

    }

    public AsterToolWindowContent() {
        super();
        init();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AsterToolWindowContent");
        frame.setContentPane(new AsterToolWindowContent().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



    private JPanel panel;
    private JPanel packUtilBox;
    private JLabel javaCodePathLabel;
    private JLabel jsCodePathLabel;
    private JLabel patchLibPathLabel;

    private JTextField javaCodePathTextBox;
    private JTextField jsCodePathTextBox;
    private JTextField patchLibPathTextBox;


    private JButton javaCodePathBrowseButton;
    private JButton jsCodePathBrowseButton;
    private JButton patchLibPathBrowseButton;
    private JButton exportButton;


    private void init(){
        Set okFileJava = new HashSet();
        okFileJava.add(".zip");
        javaCodePathBrowseButton.addActionListener(e -> {
            MyFileChooserDescriptor descriptor = new MyFileChooserDescriptor(
                    true, false, true, false, false, false);
            //descriptor.withFileFilter(okFileJava);
            descriptor.pushShowFileType("zip");
            
            //VirtualFileSystemEntry();
            VirtualFile[] virtualFiles = FileChooser.chooseFiles(descriptor, project, null);
                if (virtualFiles != null && virtualFiles.length>0 && virtualFiles[0]!=null ){
                    String path = virtualFiles[0].getPath();
                    javaCodePathTextBox.setText(path);
                }
        });


    }




    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }



}
