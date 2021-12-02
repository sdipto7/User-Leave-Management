package net.therap.leavemanagement.controller;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.helper.AuthorizationHelper;
import net.therap.leavemanagement.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Autowired
    private LeaveService leaveService;

    @RequestMapping("/userLeaveList")
    public String showUserLeaveList(HttpSession session, ModelMap model) {
        authorizationHelper.checkAccess(Arrays.asList(Designation.HUMAN_RESOURCE, Designation.TEAM_LEAD), session);

        model.addAttribute("leaveList", leaveService.findAll());

        return LEAVE_LIST_PAGE;
    }
}
