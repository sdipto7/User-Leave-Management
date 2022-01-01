package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.helper.NotificationHelper;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author rumi.dipto
 * @since 12/12/21
 */
@Controller
@RequestMapping("/notification")
public class NotificationController {

    public static final String NOTIFICATION_PAGE = "/notification";

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Autowired
    private NotificationHelper notificationHelper;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String showNotification(@RequestParam(value = "userId") long userId, ModelMap modelMap) {
        User user = userService.find(userId);
        authorizationHelper.checkAccess(user);

        notificationHelper.setupNotificationData(user, modelMap);

        return NOTIFICATION_PAGE;
    }
}
