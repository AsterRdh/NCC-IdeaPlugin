package space.cyberaster.common.ncc.plugin.NCCPlugin.toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

public class AsterToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        AsterToolWindowContent toolWindowContent = new AsterToolWindowContent(toolWindow,project);
        ContentManager contentManager = toolWindow.getContentManager();
        JBScrollPane scrollPane = new JBScrollPane(toolWindowContent.getPanel());
        Content content =  contentManager.getFactory().createContent(scrollPane, "", false);
        toolWindow.getContentManager().addContent(content);
    }


}
