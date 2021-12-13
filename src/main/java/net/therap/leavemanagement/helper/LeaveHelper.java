package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.*;
import net.therap.leavemanagement.service.NotificationService;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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
    private NotificationService notificationService;

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

    public void showListByPage(List<Leave> leaveList, String page, HttpSession session) {
        PagedListHolder<Leave> leavePagedListHolder = new PagedListHolder<>();

        if (Objects.isNull(page)) {
            leavePagedListHolder.setSource(leaveList);
            leavePagedListHolder.setPageSize(5);
            session.setAttribute("leavePagedListHolder", leavePagedListHolder);
        } else {
            leavePagedListHolder = (PagedListHolder<Leave>) session.getAttribute("leavePagedListHolder");
            int pageNumber = Integer.parseInt(page);
            leavePagedListHolder.setPage(pageNumber - 1);
        }
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

    public void setNewLeaveNotificationByUserDesignation(Leave leave) {
        User user = leave.getUser();

        Notification notification = new Notification();
        notification.setSeen(false);

        if (user.getDesignation().equals(Designation.HR_EXECUTIVE)) {
            notification.setUser(user);
            notification.setMessage("Leave request is added and auto approved");
        } else if (user.getDesignation().equals(Designation.TEAM_LEAD)) {
            User hrExecutive = userService.findHrExecutive();
            notification.setUser(hrExecutive);
            notification.setMessage(user.getFirstName() + " requested for a " +
                    leave.getLeaveType().getNaturalName() + "leave");
        } else {
            User teamLead = userManagementService.findTeamLeadByUserId(user.getId());
            notification.setUser(teamLead);
            notification.setMessage(user.getFirstName() + " requested for a " +
                    leave.getLeaveType().getNaturalName() + "leave");
        }

        notificationService.saveOrUpdate(notification);
    }

    public void setLeaveStatusNotificationByUserDesignation(Leave leave, String status) {
        User user = leave.getUser();

        Notification notification = new Notification();
        notification.setSeen(false);
        notification.setUser(user);

        if (user.getDesignation().equals(Designation.TEAM_LEAD)) {
            notification.setMessage("Your leave request is " + status + " by HR");
        } else if (user.getDesignation().equals(Designation.DEVELOPER) || user.getDesignation().equals(Designation.TESTER)) {
            if (leave.getLeaveStatus().equals(LeaveStatus.PENDING_BY_HR_EXECUTIVE)) {
                notification.setMessage("Your leave request is " + status + " by your Team Lead");
            } else if (leave.getLeaveStatus().equals(LeaveStatus.APPROVED_BY_HR_EXECUTIVE)) {
                notification.setMessage("Your leave request is " + status + " by HR");
            }
        }

        notificationService.saveOrUpdate(notification);
    }
}
