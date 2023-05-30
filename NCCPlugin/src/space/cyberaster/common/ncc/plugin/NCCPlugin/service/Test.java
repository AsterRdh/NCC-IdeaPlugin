package space.cyberaster.common.ncc.plugin.NCCPlugin.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface Test {
    static Test getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, Test.class);
    }
}
