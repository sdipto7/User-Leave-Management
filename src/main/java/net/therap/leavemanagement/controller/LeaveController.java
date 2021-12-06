package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveType;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.helper.LeaveHelper;
import net.therap.leavemanagement.helper.LeaveStatHelper;
import net.therap.leavemanagement.service.LeaveService;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import net.therap.leavemanagement.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;

import static net.therap.leavemanagement.controller.LeaveController.LEAVE_COMMAND;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
@RequestMapping("/leave")
@SessionAttributes(LEAVE_COMMAND)
public class LeaveController {

    public static final String LEAVE_COMMAND = "leave";
    public static final String LEAVE_LIST_PAGE = "/leave/list";
    public static final String LEAVE_DETAILS_PAGE = "/leave/details";
    public static final String LEAVE_SAVE_PAGE = "/leave/save";

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private LeaveHelper leaveHelper;

    @Autowired
    private LeaveStatHelper leaveStatHelper;

    @RequestMapping(value = "/allLeaveList", method = RequestMethod.GET)
    public String showAllLeaveList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE, Designation.TEAM_LEAD), session);

        model.addAttribute("leaveList", leaveService.findAllLeave());

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/allPendingLeaveList", method = RequestMethod.GET)
    public String showAllPendingLeaveList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE, Designation.TEAM_LEAD), session);

        model.addAttribute("leaveList", leaveService.findAllPendingLeave());

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/userLeaveList", method = RequestMethod.GET)
    public String showUserLeaveList(@RequestParam long userId,
                                    HttpSession session,
                                    ModelMap model) {

        User user = userService.find(userId);
        authorizationHelper.checkAccess(user, session);

        model.addAttribute("leaveList", leaveService.findUserLeaveList(userId));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/userPendingLeaveList", method = RequestMethod.GET)
    public String showUserPendingLeaveList(@RequestParam long userId,
                                           HttpSession session,
                                           ModelMap model) {

        User user = userService.find(userId);
        authorizationHelper.checkAccess(user, session);

        model.addAttribute("leaveList", leaveService.findUserPendingLeaveList(userId));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String showDetails(@RequestParam long id,
                              HttpSession session,
                              ModelMap model) {

        Leave leave = leaveService.find(id);
        leaveHelper.checkAccessByUserDesignation(leave.getUser().getId(), session);

        model.addAttribute(LEAVE_COMMAND, leave);

        return LEAVE_DETAILS_PAGE;
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showForm(@RequestParam long userId,
                           HttpSession session,
                           ModelMap model) {

        User user = userService.find(userId);
        authorizationHelper.checkAccess(user, session);

        Leave leave = leaveHelper.getLeaveByUserDesignation(user);
        model.addAttribute(LEAVE_COMMAND, leave);
        model.addAttribute("leaveTypeList", Arrays.asList(LeaveType.values()));

        return LEAVE_SAVE_PAGE;
    }

    @RequestMapping(value = "/submit", params = "action_save_or_update", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute(LEAVE_COMMAND) Leave leave,
                               Errors errors,
                               SessionStatus sessionStatus,
                               HttpSession session,
                               ModelMap model,
                               RedirectAttributes redirectAttributes) {

        User user = leave.getUser();
        authorizationHelper.checkAccess(user, session);

        if (errors.hasErrors()) {
            model.addAttribute("leaveTypeList", Arrays.asList(LeaveType.values()));

            return LEAVE_SAVE_PAGE;
        }

        leaveService.saveOrUpdate(leave);

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "Leave request is submitted successfully");

        return Constants.SUCCESS_URL;
    }

    @RequestMapping(value = "/submit", params = "action_delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute(LEAVE_COMMAND) Leave leave,
                         HttpSession session,
                         RedirectAttributes redirectAttributes,
                         SessionStatus sessionStatus) {

        User user = leave.getUser();
        authorizationHelper.checkAccess(user, session);

        leaveService.delete(leave);

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "Leave Request is successfully deleted");

        return Constants.SUCCESS_URL;
    }

    @RequestMapping(value = "/action", params = "action_approve", method = RequestMethod.POST)
    public String approveRequest(@ModelAttribute(LEAVE_COMMAND) Leave leave,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes,
                                 SessionStatus sessionStatus) {

        authorizationHelper.checkAccess(Arrays.asList(Designation.HR_EXECUTIVE, Designation.TEAM_LEAD), session);

        User teamLead = userManagementService.findTeamLeadByUserId(leave.getUser().getId());
        authorizationHelper.checkTeamLead(teamLead, session);

        leaveHelper.updateLeaveStatus(leave, session);

        leaveService.saveOrUpdate(leave);

        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("doneMessage",
                "Leave request is approved");

        return Constants.SUCCESS_URL;
    }
}
