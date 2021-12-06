package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveStatus;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author rumi.dipto
 * @since 12/2/21
 */
@Component
public class LeaveHelper {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private AuthorizationHelper authorizationHelper;

    public void checkAccessByUserDesignation(long userId, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");
        User user = userService.find(userId);

        if (sessionUser.getDesignation().equals(user.getDesignation())) {
            authorizationHelper.checkAccess(user, session);
        } else if (sessionUser.getDesignation().equals(Designation.TEAM_LEAD) &&
                (user.getDesignation().equals(Designation.DEVELOPER) ||
                        user.getDesignation().equals(Designation.TESTER))) {

            User teamLead = userManagementService.findTeamLeadByUserId(userId);
            authorizationHelper.checkTeamLead(teamLead, session);
        }
    }

    public Leave getLeaveByUserDesignation(User user) {
        Leave leave = new Leave();
        leave.setUser(user);

        if (user.getDesignation().equals(Designation.HR_EXECUTIVE)) {
            leave.setLeaveStatus(LeaveStatus.APPROVED_BY_HR_EXECUTIVE);
        } else if (user.getDesignation().equals(Designation.TEAM_LEAD)) {
            leave.setLeaveStatus(LeaveStatus.PENDING_BY_HR_EXECUTIVE);
        } else {
            leave.setLeaveStatus(LeaveStatus.PENDING_BY_TEAM_LEAD);
        }

        return leave;
    }

    public void updateLeaveStatusToApprove(Leave leave, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if ((sessionUser.getDesignation().equals(Designation.TEAM_LEAD)) &&
                (leave.getLeaveStatus().equals(LeaveStatus.PENDING_BY_TEAM_LEAD))) {

            leave.setLeaveStatus(LeaveStatus.PENDING_BY_HR_EXECUTIVE);

        } else if ((sessionUser.getDesignation().equals(Designation.HR_EXECUTIVE)) &&
                (leave.getLeaveStatus().equals(LeaveStatus.PENDING_BY_HR_EXECUTIVE))) {

            leave.setLeaveStatus(LeaveStatus.APPROVED_BY_HR_EXECUTIVE);

        }
    }

    public void updateLeaveStatusToDeny(Leave leave, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if ((sessionUser.getDesignation().equals(Designation.TEAM_LEAD)) &&
                (leave.getLeaveStatus().equals(LeaveStatus.PENDING_BY_TEAM_LEAD))) {

            leave.setLeaveStatus(LeaveStatus.DENIED_BY_TEAM_LEAD);

        } else if ((sessionUser.getDesignation().equals(Designation.HR_EXECUTIVE)) &&
                (leave.getLeaveStatus().equals(LeaveStatus.PENDING_BY_HR_EXECUTIVE))) {

            leave.setLeaveStatus(LeaveStatus.DENIED_BY_HR_EXECUTIVE);

        }
    }
}
