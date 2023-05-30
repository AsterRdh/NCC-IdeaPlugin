package space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class NccHomeComboBoxModel extends DefaultComboBoxModel<String> {

    private  List<NccHomeBean> allHome;

    public NccHomeComboBoxModel(String[] items, List<NccHomeBean> allHome) {
        super(items);
        this.allHome = allHome;
    }

    public List<NccHomeBean> getAllHome() {
        return allHome;
    }

    public void setAllHome(List<NccHomeBean> allHome) {
        this.allHome = allHome;
    }
}
