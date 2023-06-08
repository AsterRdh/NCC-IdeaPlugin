package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import space.cyberaster.common.ncc.plugin.NCCPlugin.base.NotificationGroups;
import space.cyberaster.common.ncc.plugin.NCCPlugin.beans.actions.CopyResBean;
import space.cyberaster.common.ncc.plugin.NCCPlugin.enums.FileType;
import space.cyberaster.common.ncc.plugin.NCCPlugin.settings.BaseSetting;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.CopyConUtils;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CopyToNCHome extends AnAction {
    protected VirtualFile[] selectFiles;

    @Override
    public void update(AnActionEvent event) {//根据扩展名是否是支持，显示隐藏此Action
        super.update(event);
        selectFiles = LangDataKeys.VIRTUAL_FILE_ARRAY.getData(event.getDataContext());
        boolean okFile = false;
        for (VirtualFile file : selectFiles) {
            if (file != null){
                FileType type = FileType.getFileType(file);
                switch (type){
                    case UNKNOWN:
                        okFile = false;
                        break;
                    case DIRECTORY:
                        String path = file.getPath();
                        okFile = Pattern.matches(".*/yyconfig",path)
                                || Pattern.matches(".*/yyconfig/.*",path)
                                || file.getName().equals("META-INF");
                        break;
                    default:
                        okFile = true;
                }
            }
            if (okFile) break;
        }
        event.getPresentation().setVisible(okFile);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        CopyConUtils copyConUtils = new CopyConUtils();
        List<VirtualFile> okFiles = copyConUtils.getConFile(selectFiles);
        List<VirtualFile> fFiles = FileUtils.getOnlyFile(okFiles);

        String nccHome = BaseSetting.getInstance(e.getProject()).getNccHome();
        CopyResBean resBean = copyConUtils.copyToNCCHome(nccHome,fFiles,null);

        NotificationGroup notificationGroup = new NotificationGroup(NotificationGroups.COPY_TO_NCCHOME, NotificationDisplayType.BALLOON,
                true);
        /**
         * content :  通知内容
         * type  ：通知的类型，warning,info,error
         */
        Notification notification = notificationGroup.createNotification(
                "复制配置文件到NCC Home",
                "复制成功",
                resBean.toString(),
                NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }
}
