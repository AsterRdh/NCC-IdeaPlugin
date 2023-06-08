package space.cyberaster.common.ncc.plugin.NCCPlugin.frame;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.StringUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class CreateYYConfigFrame  extends DialogWrapper {
    private Project project;

    private String clientPath ;
    private String moduleName ;
    private String itemName ;

    private JTextField moduleBox = new JTextField(35);
    private JTextField itemBox = new JTextField(35);
    private JTextField typeBox = new JTextField(35);
    private JTextField appCodeBox = new JTextField(35);

    public CreateYYConfigFrame (@Nullable Project project, String clientPath,String moduleName, String itemName) {
        super(project);

        this.project = project;
        this.clientPath = clientPath;
        this.moduleName = moduleName;
        this.itemName = itemName;
        init();
        setTitle("创建yyConfig目录");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {

        Box panelTop = Box.createVerticalBox();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JLabel jLabel1 = new JLabel("模块名称");
        JLabel jLabel2 = new JLabel("组件名称");
        JLabel jLabel3 = new JLabel("分类名称");
        JLabel jLabel4 = new JLabel("接口文件前缀");

        moduleBox.setText(moduleName);
        itemBox.setText(itemName);

        panel1.add(jLabel1);
        panel1.add(moduleBox);
        panel2.add(jLabel2);
        panel2.add(itemBox);
        panel3.add(jLabel3);
        panel3.add(typeBox);
        panel4.add(jLabel4);
        panel4.add(appCodeBox);


        panelTop.add(panel1);
        panelTop.add(panel2);
        panelTop.add(panel3);
        panelTop.add(panel4);

        return panelTop;
    }

    @Override
    protected void doOKAction() {
        String basePath = clientPath+"/yyconfig/modules/"+moduleBox.getText()+"/"+itemBox.getText()+"/";
        basePath += StringUtils.isNotBlank(typeBox.getText())? typeBox.getText()+"/config/":"config/";

        File action = new File(basePath+"action");
        File authorize = new File(basePath+"authorize");
        File userdef = new File(basePath+"userdef");

        if (!userdef.exists()){
            userdef.mkdirs();
        }
        if (!action.exists()){
            action.mkdirs();
        }
        if (!authorize.exists()){
            authorize.mkdirs();
        }

        //构建基础action和authorize
        String apCode = appCodeBox.getText();
        if (StringUtils.isNotBlank(appCodeBox.getText())){
            try {
                String actionStr =
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                                "<actions>\n"+
                                "</actions>";
                File fileAction = new File(action.getAbsolutePath()+"/"+apCode+"_action.xml");
                FileUtils.write(fileAction,actionStr,"UTF-8");
                String authorizeStr =
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n"+
                        "<authorizes>\n" +
                        "  <authorize>\n"+
                        "    <appcode>*</appcode>\n"+
                        "    <actions>\n"+
                        "    </actions>\n"+
                        "  </authorize>\n"+
                        "</authorizes>\n";

                File fileAuthorize = new File(authorize.getAbsolutePath()+"/"+apCode+"_authorize.xml");
                FileUtils.write(fileAuthorize,authorizeStr,"UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }






        super.doOKAction();
    }
}
