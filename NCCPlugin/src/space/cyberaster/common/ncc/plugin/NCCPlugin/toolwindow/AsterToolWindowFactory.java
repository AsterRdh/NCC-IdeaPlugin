package space.cyberaster.common.ncc.plugin.NCCPlugin.toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class AsterToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        AsterToolWindowContent toolWindowContent = new AsterToolWindowContent(toolWindow);
        ContentManager contentManager = toolWindow.getContentManager();
        Content content =  contentManager.getFactory().createContent(toolWindowContent.getContentPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }


    private class AsterToolWindowContent{

        private final JPanel contentPanel = new JPanel();

        public AsterToolWindowContent(ToolWindow toolWindow) {
            contentPanel.setLayout(new BorderLayout(0, 20));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
            contentPanel.add(creatToolBoxPanel(), BorderLayout.PAGE_START);
            //contentPanel.add(creatToolBoxPanel(toolWindow), BorderLayout.CENTER);
            //updateCurrentDateTime();
        }

        public JPanel creatToolBoxPanel(){
            JPanel jPanel=new JPanel();

            return jPanel;
        }

        public JPanel getContentPanel() {
            return contentPanel;
        }
    }
}
