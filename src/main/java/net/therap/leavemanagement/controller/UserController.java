package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.helper.UserHelper;
import net.therap.leavemanagement.service.LeaveStatService;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static net.therap.leavemanagement.controller.UserController.USER_COMMAND;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
@RequestMapping("/user")
@SessionAttributes(USER_COMMAND)
public class UserController {

    public static final String USER_COMMAND = "user";
    public static final String USER_LIST = "userList";

    @Autowired
    private UserService userService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private LeaveStatService leaveStatService;

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Autowired
    private UserHelper userHelper;

    @InitBinder(USER_COMMAND)
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);

        binder.setDisallowedFields("id", "activated", "created", "updated", "version");
        binder.setAllowedFields("firstName", "lastName", "username", "password", "designation", "salary");
    }

    @RequestMapping(value = "/teamLeadList", method = RequestMethod.GET)
    public String showTeamLeadList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HUMAN_RESOURCE), session);

        model.addAttribute(USER_LIST, userService.findAllTeamLead());

        return "user/list";
    }

    @RequestMapping(value = "/developerList", method = RequestMethod.GET)
    public String showDeveloperList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HUMAN_RESOURCE, Designation.TEAM_LEAD), session);

        model.addAttribute(USER_LIST, userService.findAllDeveloper(session));

        return "user/list";
    }

    @RequestMapping(value = "/testerList", method = RequestMethod.GET)
    public String showTesterList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HUMAN_RESOURCE, Designation.TEAM_LEAD), session);

        model.addAttribute(USER_LIST, userService.findAllTester(session));

        return "user/list";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String showDetails(@RequestParam long id,
                              HttpSession session,
                              ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(Designation.HUMAN_RESOURCE, Designation.TEAM_LEAD), session);

        User user = userService.find(id);
        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(id);

        User teamLead = userManagementService.findTeamLeadByUserId(id);
        authorizationHelper.checkTeamLead(teamLead, session);

        userHelper.setupUserListDataUnderTeamLead(user, model);

        model.addAttribute(USER_COMMAND, user);
        model.addAttribute("teamLead", teamLead);
        model.addAttribute("leaveStat", leaveStat);

        return "user/details";
    }
}
