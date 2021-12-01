package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 * @author rumi.dipto
 * @since 11/28/21
 */
@Component
public class UserHelper {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManagementService userManagementService;

    public void setupUserListDataUnderTeamLead(User user, ModelMap model) {
        if (Designation.TEAM_LEAD.equals(user.getDesignation())) {
            model.addAttribute("developerList",
                    userManagementService.findAllDeveloperUnderTeamLead(user.getId()));
            model.addAttribute("testerList",
                    userManagementService.findAllTesterUnderTeamLead(user.getId()));
        }
    }

    public User getOrCreateUser(long id) {
        return id == 0 ? new User() : userService.find(id);
    }
}
