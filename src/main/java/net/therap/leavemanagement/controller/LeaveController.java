package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.helper.LeaveHelper;
import net.therap.leavemanagement.service.LeaveService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * @author rumi.dipto
 * @since 11/25/21
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    public static final String LEAVE_LIST_PAGE = "/leave/list";
    public static final String LEAVE_DETAILS_PAGE = "/leave/details";

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveHelper leaveHelper;

    @RequestMapping(value = "/allLeaveList", method = RequestMethod.GET)
    public String showAllLeaveList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HUMAN_RESOURCE, Designation.TEAM_LEAD), session);

        model.addAttribute("leaveList", leaveService.findAllLeave());

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/allPendingLeaveList", method = RequestMethod.GET)
    public String showAllPendingLeaveList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HUMAN_RESOURCE, Designation.TEAM_LEAD), session);

        model.addAttribute("leaveList", leaveService.findAllPendingLeave());

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/userLeaveList", method = RequestMethod.GET)
    public String showUserLeaveList(@RequestParam long userId,
                                    HttpSession session,
                                    ModelMap model) {

        authorizationHelper.checkAccess(userService.find(userId), session);

        model.addAttribute("leaveList", leaveService.findUserLeaveList(userId));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/userPendingLeaveList", method = RequestMethod.GET)
    public String showUserPendingLeaveList(@RequestParam long userId,
                                           HttpSession session,
                                           ModelMap model) {

        authorizationHelper.checkAccess(userService.find(userId), session);

        model.addAttribute("leaveList", leaveService.findUserPendingLeaveList(userId));

        return LEAVE_LIST_PAGE;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public String showDetails(@RequestParam long id,
                              HttpSession session,
                              ModelMap model) {

        Leave leave = leaveService.find(id);
        leaveHelper.checkAccessByUserDesignation(leave.getUser().getId(), session);

        model.addAttribute("leave", leave);

        return LEAVE_DETAILS_PAGE;
    }
}
