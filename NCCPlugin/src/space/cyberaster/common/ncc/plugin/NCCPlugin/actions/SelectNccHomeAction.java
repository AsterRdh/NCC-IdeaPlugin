package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import space.cyberaster.common.ncc.plugin.NCCPlugin.frame.SelectNCCHomeFrame;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;

import java.util.UUID;

public class SelectNccHomeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        NotificationGroup notificationGroup = new NotificationGroup("testid", NotificationDisplayType.BALLOON, false);
        /**
         * content :  通知内容
         * type  ：通知的类型，warning,info,error
         */
        BaseSetting setting = BaseSetting.getInstance(e.getProject());
        String nccHome = setting.getNccHome();
        System.out.println(nccHome);
        new SelectNCCHomeFrame(e.getProject(),nccHome).show();
    }
}
