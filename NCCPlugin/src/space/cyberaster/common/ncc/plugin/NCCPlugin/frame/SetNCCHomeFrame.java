package space.cyberaster.common.ncc.plugin.NCCPlugin.frame;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.intellij.ui.table.JBTable;
import com.intellij.ui.components.JBScrollPane;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting.NccHomeBean;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting.NccHomeTableModel;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class SetNCCHomeFrame extends DialogWrapper {
    private Project project;
    private NccHomeTableModel tableModel;
    private Integer selectRow;

    public SetNCCHomeFrame(@Nullable Project project, List<NccHomeBean> baseData) {
        super(project);
        this.project = project;
        tableModel = new NccHomeTableModel(baseData);
        setTitle("设置NCCHome");
        setResizable(false);
        setSize(600,400);//new Dimension(600,300)
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel topPlane = new JPanel(new BorderLayout());

        JPanel tableButtonPlane = new JPanel();
        tableButtonPlane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton Button1 = new JButton("新增");
        Button1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addNewRow();
            }
        });
        tableButtonPlane.add(Button1);
        JButton Button2 = new JButton("删除");
        Button2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectRow!=null){
                    tableModel.removeRow(selectRow);
                }
            }
        });
        tableButtonPlane.add(Button2);

        topPlane.add(tableButtonPlane,BorderLayout.NORTH);

        JBTable jTable = new JBTable(tableModel);
        tableModel.setComponent(jTable);
        tableModel.updateColWidth();
        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = jTable.getSelectedRow();
                    selectRow = selectedRow;
                }else {
                    selectRow = null;
                }
            }
        });
        //updateColWidth(jTable);

        JBScrollPane jScrollPane=  new JBScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(600,300));
        topPlane.add(jScrollPane,BorderLayout.CENTER);
        return topPlane;
    }

    @Override
    protected void doOKAction() {
        BaseSetting setting = BaseSetting.getInstance(project);
        List<NccHomeBean> list = tableModel.toNccHomeBeanData();
        list.stream().filter(i->i.isSelected()).findFirst().ifPresent(i->setting.setNccHome(i.getPath()));
        setting.setAllNccHomes(list);

        super.doOKAction();
    }

    public void updateColWidth(JBTable jTable) {
        TableColumnModel columnModel = jTable.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            switch (i){
                case 0:
                case 1:
                    column.setPreferredWidth(44);break;
                case 2:
                    column.setPreferredWidth(100);break;
                case 4:
                    column.setPreferredWidth(200);break;
                default:
            }
        }
    }
}
