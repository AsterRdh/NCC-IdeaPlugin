package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import space.cyberaster.common.ncc.plugin.NCCPlugin.base.NotificationGroups;
import space.cyberaster.common.ncc.plugin.NCCPlugin.frame.SetModuleFrame;

public class CopyToNCHomeWithDefModule extends CopyToNCHome {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        SetModuleFrame frame=new SetModuleFrame(
                e.getProject(),
                selectFiles,
                (res)->{
                    NotificationGroup notificationGroup = new NotificationGroup(
                            NotificationGroups.COPY_TO_NCCHOME, NotificationDisplayType.BALLOON, true);
                    Notification notification = notificationGroup.createNotification(
                            "复制配置文件到NCC Home",
                            "复制成功",
                            res.toString(),
                            NotificationType.INFORMATION);
                    Notifications.Bus.notify(notification);
                });
        frame.show();

    }
}
