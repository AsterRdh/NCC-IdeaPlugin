package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;

public class CopyYYConfigAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        // TODO: insert action logic here
        NotificationGroup notificationGroup = new NotificationGroup("testid", NotificationDisplayType.BALLOON, false);
        /**
         * content :  通知内容
         * type  ：通知的类型，warning,info,error
         */
        Notification notification = notificationGroup.createNotification("测试通知", MessageType.INFO);
        Notifications.Bus.notify(notification);
    }
}