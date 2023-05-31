package space.cyberaster.common.ncc.plugin.NCCPlugin.actions;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.IOUtils;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.FileUtils;
import space.cyberaster.common.ncc.plugin.NCCPlugin.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsterCaseToUTF8 extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        // TODO: insert action logic here
        VirtualFile[] selectFiles = LangDataKeys.VIRTUAL_FILE_ARRAY.getData(event.getDataContext());
        List<String> cantMatchCharsetList = new ArrayList<>();
        List<String> ioErrorList = new ArrayList<>();
        List<String> okList = new ArrayList<>();
        File[] allFiles = FileUtils.getAllFiles(selectFiles,".*\\.java");

        for (File file : allFiles) {

            try {
                CharsetDetector detector = new CharsetDetector();
                detector.setText(org.apache.commons.io.FileUtils.readFileToByteArray(file));
                CharsetMatch match = detector.detect();
                if (match == null){
                    cantMatchCharsetList.add(file.getName());
                }else {
                    if (!match.getName().toUpperCase().equals("UTF-8")){
                        String data = org.apache.commons.io.FileUtils.readFileToString(file, match.getName());
                        org.apache.commons.io.FileUtils.write(file,data,"UTF-8");
                        okList.add(file.getName()+" "+match.getName()+"->UTF-8");
                    }
                }

            } catch (IOException e) {
                ioErrorList.add(file.getName());
                e.printStackTrace();
            }
        }


        StringBuilder content = new StringBuilder();
        StringUtils.appendToStringBuilder(cantMatchCharsetList,"以下文件无法自动匹配字符集",content);
        StringUtils.appendToStringBuilder(ioErrorList,"以下文件出现IO错误",content);
        StringUtils.appendToStringBuilder(okList,"以下文件转换成功",content);


        NotificationGroup notificationGroup = new NotificationGroup("testid", NotificationDisplayType.BALLOON, false);
        /**
         * content :  通知内容
         * type  ：通知的类型，warning,info,error
         */
        Notification notification = notificationGroup.createNotification(
                "转换文件字符集到UTF-8",
                "转换完成",
                content.toString(),
                NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);

    }
}
