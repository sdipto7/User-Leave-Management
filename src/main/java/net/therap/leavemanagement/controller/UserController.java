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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static net.therap.leavemanagement.domain.Designation.HR_EXECUTIVE;
import static net.therap.leavemanagement.domain.Designation.TEAM_LEAD;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
@RequestMapping("/user")
@SessionAttributes({USER_COMMAND, USER_COMMAND_SAVE, USER_COMMAND_PROFILE})
public class UserController {

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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static final String USER_COMMAND = "user";
    public static final String USER_COMMAND_SAVE = "userSaveCommand";
    public static final String USER_COMMAND_PROFILE = "userProfileCommand";
    public static final String USER_PROFILE_PAGE = "/user/profile";
    public static final String USER_LIST_PAGE = "/user/list";
    public static final String USER_DETAILS_PAGE = "/user/details";
    public static final String USER_SAVE_PAGE = "/user/save";

    @InitBinder(USER_COMMAND_SAVE)
    public void initBinderToSaveUser(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setAllowedFields("user.firstName", "user.lastName", "user.username",
                "user.password", "user.designation", "user.salary", "teamLead");
        binder.addValidators(userSaveCommandValidator);
    }

    @InitBinder(USER_COMMAND_PROFILE)
    public void initBinderToUpdateProfile(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setAllowedFields("user", "currentPassword", "newPassword", "confirmedNewPassword");
        binder.addValidators(userProfileCommandValidator);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    private String showProfile(@RequestParam long id,
                               HttpSession session,
                               ModelMap model) {

        User user = userService.find(id);
        authorizationHelper.checkAccess(user);

        UserProfileCommand userProfileCommand = new UserProfileCommand();
        userProfileCommand.setUser(user);

        model.addAttribute(USER_COMMAND_PROFILE, userProfileCommand);
        model.addAttribute("teamLead", userManagementService.findTeamLeadByUserId(id));
        model.addAttribute("leaveStat", leaveStatService.findLeaveStatByUserId(id));

        return USER_PROFILE_PAGE;
    }

    @RequestMapping(value = "/teamLeadList", method = RequestMethod.GET)
    public String showTeamLeadList(@RequestParam(defaultValue = "1") String page,
                                   HttpSession session,
                                   ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(HR_EXECUTIVE));

        List<User> teamLeadList = userService.findAllTeamLead(Integer.parseInt(page));

        model.addAttribute("userList", teamLeadList);
        model.addAttribute("pageNumber", userHelper.getTotalPageNumber(Math.toIntExact(userService.countTeamLead())));

        return USER_LIST_PAGE;
    }

    @RequestMapping(value = "/developerList", method = RequestMethod.GET)
    public String showDeveloperList(@RequestParam(defaultValue = "1") String page,
                                    HttpSession session,
                                    ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(HR_EXECUTIVE, TEAM_LEAD));

        User sessionUser = (User) session.getAttribute("SESSION_USER");

        List<User> developerList = userService.findAllDeveloper(sessionUser, Integer.parseInt(page));

        model.addAttribute("userList", developerList);
        model.addAttribute("pageNumber", userHelper.getTotalPageNumber(Math.toIntExact(userService.countDeveloper(sessionUser))));

        return USER_LIST_PAGE;
    }

    @RequestMapping(value = "/testerList", method = RequestMethod.GET)
    public String showTesterList(@RequestParam(defaultValue = "1") String page,
                                 HttpSession session,
                                 ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(HR_EXECUTIVE, TEAM_LEAD));

        User sessionUser = (User) session.getAttribute("SESSION_USER");

        List<User> testerList = userService.findAllTester(sessionUser, Integer.parseInt(page));

        model.addAttribute("userList", testerList);
        model.addAttribute("pageNumber", userHelper.getTotalPageNumber(Math.toIntExact(userService.countTester(sessionUser))));

        return USER_LIST_PAGE;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String showDetails(@RequestParam long id,
                              HttpSession session,
                              ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(HR_EXECUTIVE, TEAM_LEAD));

        User user = userService.find(id);
        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(id);

        userHelper.checkAndAddAuthorizedTeamLeadIfExist(user, session, model);

        userHelper.setupDataIfTeamLead(user, model);

        model.addAttribute(USER_COMMAND, user);
        model.addAttribute("leaveStat", leaveStat);

        return USER_DETAILS_PAGE;
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showForm(@RequestParam(defaultValue = "0") long id,
                           HttpSession session,
                           ModelMap model) {

        authorizationHelper.checkAccess(Arrays.asList(HR_EXECUTIVE));

        User user = userHelper.getOrCreateUser(id);
        UserSaveCommand userSaveCommand = new UserSaveCommand();
        userSaveCommand.setUser(user);

        userHelper.setConditionalDataForUserSaveView(user, model);

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

        authorizationHelper.checkAccess(Arrays.asList(HR_EXECUTIVE));

        if (errors.hasErrors()) {
            userHelper.setConditionalDataForUserSaveView(userSaveCommand.getUser(), model);
            model.addAttribute("teamLeadList", userService.findAllTeamLead());
            model.addAttribute("designationList", Arrays.asList(Designation.values()));

            return USER_SAVE_PAGE;
        }

        userHelper.checkAndSetRoleChange(userSaveCommand);
        userService.saveOrUpdate(userSaveCommand);
        logger.info("[user_save] {} saved successfully", userSaveCommand.getUser().getFirstName());

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "User " + userSaveCommand.getUser().getFirstName() + " saved successfully");

        return "redirect:/" + Constant.SUCCESS_URL;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(@Valid @ModelAttribute(USER_COMMAND_PROFILE) UserProfileCommand userProfileCommand,
                                 Errors errors,
                                 HttpSession session,
                                 SessionStatus sessionStatus,
                                 RedirectAttributes redirectAttributes,
                                 ModelMap model) {

        User user = userProfileCommand.getUser();
        authorizationHelper.checkAccess(user);

        if (errors.hasErrors()) {
            model.addAttribute("teamLead", userManagementService.findTeamLeadByUserId(user.getId()));
            model.addAttribute("leaveStat", leaveStatService.findLeaveStatByUserId(user.getId()));

            return USER_PROFILE_PAGE;
        }

        userService.updatePassword(userProfileCommand);
        session.setAttribute("SESSION_USER", userProfileCommand.getUser());
        logger.info("[user_updatePass] {} {} updated own password successfully", user.getFirstName(), user.getLastName());

        sessionStatus.setComplete();

        redirectAttributes.addFlashAttribute("doneMessage",
                "Password updated successfully");

        return "redirect:/" + Constant.SUCCESS_URL;
    }

    @RequestMapping(value = "/submit", params = "action_delete", method = RequestMethod.POST)
    public String delete(@Valid @ModelAttribute(USER_COMMAND) User user,
                         Errors errors,
                         HttpSession session,
                         RedirectAttributes redirectAttributes,
                         SessionStatus sessionStatus) {

        authorizationHelper.checkAccess(Arrays.asList(HR_EXECUTIVE));

        if (errors.hasErrors()) {
            return USER_DETAILS_PAGE;
        }

        userService.delete(user);
        logger.info("[user_delete] {} {} is deleted successfully", user.getFirstName(), user.getLastName());

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "User " + user.getFirstName() + " is deleted successfully");

        return "redirect:/" + Constant.SUCCESS_URL;
    }
}
