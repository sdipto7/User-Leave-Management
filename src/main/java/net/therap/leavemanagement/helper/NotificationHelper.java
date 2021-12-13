package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Notification;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * @author rumi.dipto
 * @since 12/13/21
 */
@Component
public class NotificationHelper {

    @Autowired
    private NotificationService notificationService;

    public void setupNotificationData(User user, ModelMap model) {
        List<Notification> notificationList = notificationService.findAllNotification(user.getId());
        if (notificationList.size() > 0) {
            notificationList.forEach(notification -> {
                notification.setSeen(true);
                notificationService.saveOrUpdate(notification);
            });
        }

        model.addAttribute("notificationList", notificationList);
    }
}
