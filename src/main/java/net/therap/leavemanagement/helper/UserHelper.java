package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.command.UserSaveCommand;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;

import static net.therap.leavemanagement.util.Constant.pageSize;

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

    @Autowired
    private AuthorizationHelper authorizationHelper;

    public void setupDataIfTeamLead(User user, ModelMap model) {
        if (user.isTeamLead()) {
            model.addAttribute("developerList",
                    userManagementService.findAllDeveloperUnderTeamLead(user.getId()));
            model.addAttribute("testerList",
                    userManagementService.findAllTesterUnderTeamLead(user.getId()));
        }
    }

    public int getTotalPageNumber(int listSize) {
        return ((listSize % pageSize == 0) ? (listSize / pageSize) : (listSize / pageSize) + 1);
    }

    public User getOrCreateUser(long id) {
        return id == 0 ? new User() : userService.find(id);
    }

    public void checkAndSetRoleChange(UserSaveCommand userSaveCommand) {
        User commandUser = userSaveCommand.getUser();
        long id = commandUser.getId();

        if (id != 0) {
            User dbUser = userService.find(id);
            if (commandUser.isTeamLead() && ((dbUser.isDeveloper()) || (dbUser.isTester()))) {
                userSaveCommand.setRoleChanged(true);
            }
        }
    }

    public void checkAndAddAuthorizedTeamLeadIfExist(User user, HttpSession session, ModelMap model) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");
        User teamLead = userManagementService.findTeamLeadByUserId(user.getId());

        if ((user.isDeveloper() || user.isTester()) && (sessionUser.isTeamLead())) {
            authorizationHelper.checkAccess(teamLead);
        }
        model.addAttribute("teamLead", teamLead);
    }

    public void setConditionalDataForUserSaveView(User user, ModelMap model) {
        if (user.isNew() || (!user.isNew() && (user.isDeveloper() || user.isTester()))) {
            model.addAttribute("canSelectDesignation", true);
        }

        if (user.isNew()) {
            model.addAttribute("canInputPassword", true);
        }
    }
}
