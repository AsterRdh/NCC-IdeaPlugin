package space.cyberaster.common.ncc.plugin.NCCPlugin.settings;

import com.google.gson.Gson;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting.NccHomeBean;

import java.util.ArrayList;
import java.util.List;

@State(name = "AsterNCCPluginSetting",storages = @Storage("AsterNCCPluginSetting.xml"))

public class BaseSetting implements PersistentStateComponent<BaseSetting> {

    public static BaseSetting getInstance(Project project) {
        return project.getService(BaseSetting.class);
    }

    public static class State{
        public String nccHome;

        public List<NccHomeBean> allNccHomes;

        public String getNccHome() {
            return nccHome;
        }

        public void setNccHome(String nccHome) {
            this.nccHome = nccHome;
        }

        public List<NccHomeBean> getAllNccHomes() {
            return allNccHomes;
        }

        public void setAllNccHomes(List<NccHomeBean> allNccHomes) {
            this.allNccHomes = allNccHomes;
        }
    }


    public State state = new State();





    @Nullable
    @Override
    public BaseSetting getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BaseSetting baseSetting) {
        this.state.nccHome = baseSetting.state.nccHome;
        this.state.allNccHomes = baseSetting.state.allNccHomes;
    }

    public String getNccHome() {
        return state.nccHome;
    }

    public void setNccHome(String nccHome) {
        this.state.nccHome = nccHome;
    }

    public List<NccHomeBean> getAllNccHomes() {
        return state.allNccHomes;
    }

    public void setAllNccHomes(List<NccHomeBean> allNccHomes) {
        state.allNccHomes=allNccHomes;
    }
}
