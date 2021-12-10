package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.command.UserSaveCommand;
import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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

    public void showListByPage(List<User> userList, String page, HttpSession session) {
        PagedListHolder<User> userPagedListHolder = new PagedListHolder<>();

        if (Objects.isNull(page)) {
            userPagedListHolder.setSource(userList);
            userPagedListHolder.setPageSize(5);
            session.setAttribute("userPagedListHolder", userPagedListHolder);
        } else {
            userPagedListHolder = (PagedListHolder<User>) session.getAttribute("userPagedListHolder");
            int pageNumber = Integer.parseInt(page);
            userPagedListHolder.setPage(pageNumber - 1);
        }
    }

    public User getOrCreateUser(long id) {
        return id == 0 ? new User() : userService.find(id);
    }

    public void checkRoleChange(UserSaveCommand userSaveCommand) {
        User commandUser = userSaveCommand.getUser();
        long id = commandUser.getId();

        if (id != 0) {
            User dbUser = userService.find(id);
            if (commandUser.getDesignation().equals(Designation.TEAM_LEAD) &&
                    ((dbUser.getDesignation().equals(Designation.DEVELOPER))
                            || (dbUser.getDesignation().equals(Designation.TESTER)))) {

                userSaveCommand.setRoleChanged(true);
            }
        }
    }
}
