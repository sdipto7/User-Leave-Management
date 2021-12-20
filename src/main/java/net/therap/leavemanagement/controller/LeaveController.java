package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveType;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.helper.LeaveHelper;
import net.therap.leavemanagement.helper.UserHelper;
import net.therap.leavemanagement.service.LeaveService;
import net.therap.leavemanagement.service.UserService;
import net.therap.leavemanagement.util.Constant;
import net.therap.leavemanagement.validator.LeaveValidator;
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

import static net.therap.leavemanagement.controller.LeaveController.LEAVE_COMMAND;
import static net.therap.leavemanagement.domain.Designation.HR_EXECUTIVE;
import static net.therap.leavemanagement.domain.Designation.TEAM_LEAD;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
@RequestMapping("/leave")
@SessionAttributes(LEAVE_COMMAND)
public class LeaveController {

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveHelper leaveHelper;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private LeaveValidator leaveValidator;

    private static final Logger logger = LoggerFactory.getLogger(LeaveController.class);

    public static final String LEAVE_COMMAND = "leave";
    public static final String LEAVE_LIST_PAGE = "/leave/list";
    public static final String LEAVE_DETAILS_PAGE = "/leave/details";
    public static final String LEAVE_SAVE_PAGE = "/leave/save";

    @InitBinder(LEAVE_COMMAND)
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setAllowedFields("user", "leaveType", "leaveStatus", "note", "startDate", "endDate");
        binder.addValidators(leaveValidator);
    }

    @RequestMapping(value = "/LeaveList", method = RequestMethod.GET)
    public String showLeaveList(@RequestParam(defaultValue = "1") String page,
                                ModelMap model) {

        authorizationHelper.checkAccess(HR_EXECUTIVE, TEAM_LEAD);

        List<Leave> allUserLeaveList = leaveService.findAllLeave(Integer.parseInt(page));

        model.addAttribute("leaveList", allUserLeaveList);
        model.addAttribute("pageNumber", leaveHelper.getTotalPageNumber(Math.toIntExact(leaveService.countAllLeave())));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/pendingLeaveList", method = RequestMethod.GET)
    public String showPendingLeaveList(@RequestParam(defaultValue = "1") String page,
                                       ModelMap model) {

        authorizationHelper.checkAccess(HR_EXECUTIVE, TEAM_LEAD);

        List<Leave> allUserPendingLeaveList = leaveService.findAllPendingLeave(Integer.parseInt(page));

        model.addAttribute("leaveList", allUserPendingLeaveList);
        model.addAttribute("pageNumber", leaveHelper.getTotalPageNumber(Math.toIntExact(leaveService.countAllPendingLeave())));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/userLeaveList", method = RequestMethod.GET)
    public String showUserLeaveList(@RequestParam long userId,
                                    @RequestParam(defaultValue = "1") String page,
                                    ModelMap model) {

        User user = userService.find(userId);
        authorizationHelper.checkAccess(user);

        List<Leave> userLeaveList = leaveService.findUserLeaveList(userId, Integer.parseInt(page));

        model.addAttribute("leaveList", userLeaveList);
        model.addAttribute("pageNumber", leaveHelper.getTotalPageNumber(Math.toIntExact(leaveService.countUserLeave(user.getId()))));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/userPendingLeaveList", method = RequestMethod.GET)
    public String showUserPendingLeaveList(@RequestParam long userId,
                                           @RequestParam(defaultValue = "1") String page,
                                           ModelMap model) {

        User user = userService.find(userId);
        authorizationHelper.checkAccess(user);

        List<Leave> userPendingLeaveList = leaveService.findUserPendingLeaveList(userId, Integer.parseInt(page));

        model.addAttribute("leaveList", userPendingLeaveList);
        model.addAttribute("pageNumber", leaveHelper.getTotalPageNumber(Math.toIntExact(leaveService.countUserPendingLeave(user.getId()))));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String showDetails(@RequestParam long id,
                              HttpSession session,
                              ModelMap model) {

        Leave leave = leaveService.find(id);
        leaveHelper.checkAccessByUserDesignation(leave.getUser(), session, model);

        leaveHelper.setConditionalDataForLeaveDetailsView(leave, session, model);

        model.addAttribute(LEAVE_COMMAND, leave);

        return LEAVE_DETAILS_PAGE;
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showForm(@RequestParam long userId,
                           ModelMap model) {

        User user = userService.find(userId);
        authorizationHelper.checkAccess(user);

        Leave leave = leaveHelper.getLeaveByUserDesignation(user);
        model.addAttribute(LEAVE_COMMAND, leave);
        model.addAttribute("leaveTypeList", Arrays.asList(LeaveType.values()));

        return LEAVE_SAVE_PAGE;
    }

    @RequestMapping(value = "/submit", params = "action_save_or_update", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute(LEAVE_COMMAND) Leave leave,
                               Errors errors,
                               SessionStatus sessionStatus,
                               ModelMap model,
                               RedirectAttributes redirectAttributes) {

        User user = leave.getUser();
        authorizationHelper.checkAccess(user);

        if (errors.hasErrors()) {
            model.addAttribute("leaveTypeList", Arrays.asList(LeaveType.values()));

            return LEAVE_SAVE_PAGE;
        }

        leaveService.saveOrUpdate(leave);

        leaveHelper.setNewLeaveNotificationByUserDesignation(leave);
        logger.info("[leave_save] {} {}  added a new leave request", user.getFirstName(), user.getLastName());

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "Leave request is submitted successfully");

        return "redirect:/" + Constant.SUCCESS_URL;
    }

    @RequestMapping(value = "/submit", params = "action_delete", method = RequestMethod.POST)
    public String delete(@Valid @ModelAttribute(LEAVE_COMMAND) Leave leave,
                         Errors errors,
                         RedirectAttributes redirectAttributes,
                         SessionStatus sessionStatus) {

        User user = leave.getUser();
        authorizationHelper.checkAccess(user);

        if (errors.hasErrors()) {
            return LEAVE_DETAILS_PAGE;
        }

        leaveService.delete(leave);
        logger.info("[leave_save] {} {}  deleted own leave request", user.getFirstName(), user.getLastName());

        sessionStatus.setComplete();
        redirectAttributes.addAttribute("doneMessage",
                "Leave Request is successfully deleted");

        return "redirect:/" + Constant.SUCCESS_URL;
    }

    @RequestMapping(value = "/action", params = "action_approve", method = RequestMethod.POST)
    public String approveRequest(@Valid @ModelAttribute(LEAVE_COMMAND) Leave leave,
                                 Errors errors,
                                 SessionStatus sessionStatus,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes,
                                 ModelMap model) {

        authorizationHelper.checkAccess(HR_EXECUTIVE, TEAM_LEAD);

        if (errors.hasErrors()) {
            return LEAVE_DETAILS_PAGE;
        }

        User user = leave.getUser();

        userHelper.checkAndAddAuthorizedTeamLeadIfExist(user, session, model);

        leaveHelper.updateLeaveStatusToApprove(leave, session);

        leaveService.saveOrUpdate(leave);

        leaveHelper.setLeaveStatusNotificationByUserDesignation(leave, "approved");
        logger.info("Team Lead gave approval to the leave request of {} {}", user.getFirstName(), user.getLastName());

        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("doneMessage",
                "Leave request is approved");

        return "redirect:/" + Constant.SUCCESS_URL;
    }

    @RequestMapping(value = "/action", params = "action_reject", method = RequestMethod.POST)
    public String rejectRequest(@Valid @ModelAttribute(LEAVE_COMMAND) Leave leave,
                                Errors errors,
                                SessionStatus sessionStatus,
                                HttpSession session,
                                RedirectAttributes redirectAttributes,
                                ModelMap model) {

        authorizationHelper.checkAccess(HR_EXECUTIVE, TEAM_LEAD);

        if (errors.hasErrors()) {
            return LEAVE_DETAILS_PAGE;
        }

        User user = leave.getUser();

        userHelper.checkAndAddAuthorizedTeamLeadIfExist(user, session, model);

        leaveHelper.updateLeaveStatusToDeny(leave, session);

        leaveService.saveOrUpdate(leave);

        leaveHelper.setLeaveStatusNotificationByUserDesignation(leave, "rejected");

        logger.info("Team Lead denied the leave request of {} {}", user.getFirstName(), user.getLastName());

        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("doneMessage",
                "Leave request is denied");

        return "redirect:/" + Constant.SUCCESS_URL;
    }
}
