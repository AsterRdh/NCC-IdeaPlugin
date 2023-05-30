package space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting;

import com.intellij.ui.table.JBTable;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.AsterTable;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class NccHomeTableModel implements TableModel {

    protected EventListenerList listenerList = new EventListenerList();
    private JBTable component  ;
    private static String[] colName = {"序号","默认","名称","版本","路径","备注"};
    private static Class[] colClazz = {Integer.class,Boolean.class,String.class,String.class,String.class,String.class};
    private AsterTable<Object> table = new AsterTable();

    public List<NccHomeBean> toNccHomeBeanData(){
        List<NccHomeBean> list = new ArrayList<>();
        if (!table.isEmpty()){
            int rowCount = table.getRowCount();
            for (int row = 0; row < rowCount; row++) {
                NccHomeBean bean = new NccHomeBean();
                Boolean isSelect = table.getValue(row, 1, Boolean.class);
                bean.setSelected(isSelect == null?false:isSelect);
                bean.setName(table.getValue(row, 2, String.class));
                bean.setVersion(table.getValue(row, 3, String.class));
                bean.setPath(table.getValue(row, 4, String.class));
                bean.setRemark(table.getValue(row, 5, String.class));
                list.add(bean);
            }
        }
        return list;
    }

    public NccHomeTableModel() {
        List row = new ArrayList();
        row.add(1);
        row.add(false);
        row.add("测试");
        table.addRow(row);
    }

    public NccHomeTableModel(List<NccHomeBean> baseData) {
        int index = 1;
        for (NccHomeBean baseDatum : baseData) {
            List row = new ArrayList();
            row.add(index++);
            row.add(baseDatum.isSelected());
            row.add(baseDatum.getName());
            row.add(baseDatum.getVersion());
            row.add(baseDatum.getPath());
            row.add(baseDatum.getRemark());
            table.addRow(row);
        }

    }

    public void setComponent(JBTable component) {
        this.component = component;
    }

    public void addNewRow(){

        this.addRow( new ArrayList<>());
    }

    public void addRow(List row){
        if (row.isEmpty()){
            row.add(table.getRowCount()+1);
        }else {
            row.set(0,table.getRowCount()+1);
        }
        table.addRow(row);
        update();
    }

    public void removeRow(int row){
        table.removeRow(row);
        for (int i = 0; i < table.getRowCount(); i++) {
            List<Object> row1 = table.getRow(i);
            row1.set(0,i+1);
        }

        update();
    }

    @Override
    public int getRowCount() {
        return table.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return colName.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colName[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return colClazz[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return table.getValue(rowIndex,columnIndex);
    }


    public <E> Object getValueAt(int rowIndex, int columnIndex,Class<E> clazz) {
        return table.getValue(rowIndex,columnIndex,clazz);
    }

    private void update(){
        TableModelEvent tableModelEvent = new TableModelEvent(this, TableModelEvent.HEADER_ROW);
//        for (Object o : listenerList.getListenerList()) {
//            TableModelListener listener = (TableModelListener) o;
//            listener.tableChanged(tableModelEvent);
//        }

        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableModelListener.class) {
                ((TableModelListener)listeners[i+1]).tableChanged(tableModelEvent);
            }
        }
        updateColWidth(component);
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex==1 && aValue instanceof Boolean && (Boolean)aValue){
            for (int i = 0; i < table.getRowCount(); i++) {
                if (i!=rowIndex){
                    table.setData(i,1,  false);
                }
            }
        }
        table.setData(rowIndex,columnIndex,  aValue);
        update();
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
    }
    public void updateColWidth() {
        updateColWidth(component);
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
