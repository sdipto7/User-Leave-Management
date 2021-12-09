package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.command.UserProfileCommand;
import net.therap.leavemanagement.command.UserSaveCommand;
import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.helper.UserHelper;
import net.therap.leavemanagement.service.LeaveStatService;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import net.therap.leavemanagement.util.Constant;
import net.therap.leavemanagement.validator.UserProfileCommandValidator;
import net.therap.leavemanagement.validator.UserSaveCommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static net.therap.leavemanagement.controller.UserController.*;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
@RequestMapping("/user")
@SessionAttributes({USER_COMMAND, USER_COMMAND_SAVE, USER_COMMAND_PROFILE})
public class UserController {

    public static final String USER_COMMAND = "user";
    public static final String USER_COMMAND_SAVE = "userSaveCommand";
    public static final String USER_COMMAND_PROFILE = "userProfileCommand";
    public static final String USER_PROFILE_PAGE = "/user/profile";
    public static final String USER_LIST_PAGE = "/user/list";
    public static final String USER_DETAILS_PAGE = "/user/details";
    public static final String USER_SAVE_PAGE = "/user/save";

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

    @Autowired
    private UserSaveCommandValidator userSaveCommandValidator;

    @Autowired
    private UserProfileCommandValidator userProfileCommandValidator;

    @InitBinder(USER_COMMAND_SAVE)
    public void initBinderToSaveUser(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);

        binder.setAllowedFields("user.firstName", "user.lastName", "user.username",
                "user.password", "user.designation", "user.salary", "teamLead");
        binder.addValidators(userSaveCommandValidator);
    }

    @InitBinder(USER_COMMAND_PROFILE)
    public void initBinderToUpdateProfile(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);

        binder.setAllowedFields("user", "currentPassword", "newPassword", "confirmedNewPassword");
        binder.addValidators(userProfileCommandValidator);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    private String showProfile(@RequestParam long id,
                               HttpSession session,
                               ModelMap model) {

        User user = userService.find(id);
        authorizationHelper.checkAccess(user, session);

        UserProfileCommand userProfileCommand = new UserProfileCommand();
        userProfileCommand.setUser(user);

        model.addAttribute(USER_COMMAND_PROFILE, userProfileCommand);
        model.addAttribute("teamLead", userManagementService.findTeamLeadByUserId(id));
        model.addAttribute("leaveStat", leaveStatService.findLeaveStatByUserId(id));

        return USER_PROFILE_PAGE;
    }

    @RequestMapping(value = "/teamLeadList", method = RequestMethod.GET)
    public String showTeamLeadList(@RequestParam(value = "page", required = false) String page,
                                   HttpSession session) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE), session);

        List<User> teamLeadList = userService.findAllTeamLead();
        userHelper.showListByPage(teamLeadList, page, session);

        return USER_LIST_PAGE;
    }

    @RequestMapping(value = "/developerList", method = RequestMethod.GET)
    public String showDeveloperList(@RequestParam(value = "page", required = false) String page,
                                    HttpSession session) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE, Designation.TEAM_LEAD), session);

        List<User> developerList = userService.findAllDeveloper(session);
        userHelper.showListByPage(developerList, page, session);

        return USER_LIST_PAGE;
    }

    @RequestMapping(value = "/testerList", method = RequestMethod.GET)
    public String showTesterList(@RequestParam(value = "page", required = false) String page,
                                 HttpSession session) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE, Designation.TEAM_LEAD), session);

        List<User> testerList = userService.findAllTester(session);
        userHelper.showListByPage(testerList, page, session);

        return USER_LIST_PAGE;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String showDetails(@RequestParam long id,
                              HttpSession session,
                              ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE, Designation.TEAM_LEAD), session);

        User user = userService.find(id);
        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(id);

        User teamLead = userManagementService.findTeamLeadByUserId(id);
        authorizationHelper.checkTeamLead(teamLead, session);

        userHelper.setupUserListDataUnderTeamLead(user, model);

        model.addAttribute(USER_COMMAND, user);
        model.addAttribute("teamLead", teamLead);
        model.addAttribute("leaveStat", leaveStat);

        return USER_DETAILS_PAGE;
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showForm(@RequestParam(defaultValue = "0") long id,
                           HttpSession session,
                           ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE), session);

        UserSaveCommand userSaveCommand = new UserSaveCommand();
        userSaveCommand.setUser(userHelper.getOrCreateUser(id));

        model.addAttribute(USER_COMMAND_SAVE, userSaveCommand);
        model.addAttribute("teamLeadList", userService.findAllTeamLead());
        model.addAttribute("designationList", Arrays.asList(Designation.values()));

        return USER_SAVE_PAGE;
    }

    @RequestMapping(value = "/submit", params = "action_save_or_update", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute(USER_COMMAND_SAVE) UserSaveCommand userSaveCommand,
                               Errors errors,
                               SessionStatus sessionStatus,
                               HttpSession session,
                               ModelMap model,
                               RedirectAttributes redirectAttributes) {

        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE), session);

        if (errors.hasErrors()) {
            model.addAttribute("teamLeadList", userService.findAllTeamLead());
            model.addAttribute("designationList", Arrays.asList(Designation.values()));

            return USER_SAVE_PAGE;
        }

        userService.saveOrUpdate(userSaveCommand);

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "User " + userSaveCommand.getUser().getFirstName() + " saved successfully");

        return Constant.SUCCESS_URL;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(@Valid @ModelAttribute(USER_COMMAND_PROFILE) UserProfileCommand userProfileCommand,
                                 Errors errors,
                                 HttpSession session,
                                 SessionStatus sessionStatus,
                                 RedirectAttributes redirectAttributes,
                                 ModelMap model) {

        User user = userProfileCommand.getUser();
        authorizationHelper.checkAccess(user, session);

        if (errors.hasErrors()) {
            model.addAttribute("teamLead", userManagementService.findTeamLeadByUserId(user.getId()));
            model.addAttribute("leaveStat", leaveStatService.findLeaveStatByUserId(user.getId()));

            return USER_PROFILE_PAGE;
        }

        userService.updatePassword(userProfileCommand);
        session.setAttribute("SESSION_USER", userProfileCommand.getUser());

        sessionStatus.setComplete();

        redirectAttributes.addFlashAttribute("doneMessage",
                "Password updated successfully");

        return Constant.SUCCESS_URL;
    }

    @RequestMapping(value = "/submit", params = "action_delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute(USER_COMMAND) User user,
                         HttpSession session,
                         RedirectAttributes redirectAttributes,
                         SessionStatus sessionStatus) {

        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE), session);

        userService.delete(user);

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "User " + user.getFirstName() + " is deleted successfully");

        return Constant.SUCCESS_URL;
    }
}
