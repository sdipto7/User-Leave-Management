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

    public void setupNotificationData(User user, ModelMap modelMap) {
        List<Notification> unseenNotifications = notificationService.findAllUnseenNotifications(user.getId());
        if (unseenNotifications.size() > 0) {
            unseenNotifications.forEach(notification -> {
                notification.setSeen(true);
                notificationService.saveOrUpdate(notification);
            });
        }

        modelMap.addAttribute("notificationList", unseenNotifications);
    }
}
