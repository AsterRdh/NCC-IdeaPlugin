package space.cyberaster.common.ncc.plugin.NCCPlugin.utils;

import java.util.ArrayList;
import java.util.List;

public class AsterTable<E> {
    private List<List<E>> data = new ArrayList<>();

    public int getRowCount(){
        return data.size();
    }

    public E getValue(int row,int col){
        if (data.size()<row){
            return null;
        }
        List<E> rowData = data.get(row);
        if (rowData==null || rowData.size()<=col){
            return null;
        }

        return rowData.get(col);
    }

    public <T> T getValue(int row,int col,Class<T> clazz){
        if (data.size()<row){
            return null;
        }
        List<E> rowData = data.get(row);
        if (rowData==null || rowData.size()<=col){
            return null;
        }

        return (T) rowData.get(col);
    }

    public boolean isEmpty(){
        //if (data.isEmpty()) return true;
        return data.isEmpty();
    }

    public List<E> getRow(int index){
        if (data.size()<index){
            return null;
        }else {
            return data.get(index);
        }
    }


    public void addRow(List<E> rowData){
        this.data.add(rowData);
    }

    public void setData(int row,int col,E data){
        int emptyRowCount =  (row+1) - this.data.size() ;
        if (emptyRowCount > 0 ){
            for (int i = 0; i < emptyRowCount; i++) {
                this.data.add(new ArrayList<>());
            }
        }
        List<E> rowData = this.data.get(row);
        int emptyColCount = (col+1) - rowData.size() ;
        if (emptyColCount > 0){
            for (int i = 0; i < emptyColCount; i++) {
                rowData.add(null);
            }
        }
        rowData.set(col,data);

    }


    public void removeRow(int row){
        data.remove(row);
    }

}
