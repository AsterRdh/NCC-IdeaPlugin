package space.cyberaster.common.ncc.plugin.NCCPlugin.settings;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import  com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.setting.NccHomeBean;

import java.util.List;

public class NccHomeBeanConverter extends Converter<NccHomeBean> {


    @Nullable
    @Override
    public NccHomeBean fromString(@NotNull String s) {
        return null;
    }

    @Nullable
    @Override
    public String toString(@NotNull NccHomeBean nccHomeBean) {
        return null;
    }
}
