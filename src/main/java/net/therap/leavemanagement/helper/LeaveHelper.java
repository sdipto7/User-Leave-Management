package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.Notification;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.NotificationService;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

import static net.therap.leavemanagement.domain.Designation.*;
import static net.therap.leavemanagement.domain.LeaveStatus.*;

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

    @Autowired
    private UserHelper userHelper;

    public void checkAccessByUserDesignation(User user, HttpSession session, ModelMap model) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (sessionUser.getDesignation().equals(user.getDesignation())) {
            authorizationHelper.checkAccess(user, session);
        } else if (sessionUser.getDesignation().equals(TEAM_LEAD) &&
                (user.getDesignation().equals(DEVELOPER) ||
                        user.getDesignation().equals(TESTER))) {

            userHelper.checkAuthorizedTeamLeadIfExist(user, session, model);
        }
    }

    public Leave getLeaveByUserDesignation(User user) {
        Leave leave = new Leave();
        leave.setUser(user);

        if (user.getDesignation().equals(HR_EXECUTIVE)) {
            leave.setLeaveStatus(APPROVED_BY_HR_EXECUTIVE);
        } else if (user.getDesignation().equals(TEAM_LEAD)) {
            leave.setLeaveStatus(PENDING_BY_HR_EXECUTIVE);
        } else {
            leave.setLeaveStatus(PENDING_BY_TEAM_LEAD);
        }

        return leave;
    }

    public void setConditionalDataForLeaveDetailsView(Leave leave, HttpSession session, ModelMap model) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if ((isUserTeamLead(sessionUser) && isLeaveStatusPendingByTeamLead(leave))
                || (isUserHrExecutive(sessionUser) && isLeaveStatusPendingByHrExecutive(leave))) {
            model.addAttribute("canReview", true);
        }

        if (((isUserDeveloper(sessionUser) || isUserTester(sessionUser)) && isLeaveStatusPendingByTeamLead(leave))
                || (isUserTeamLead(sessionUser) && isUserTeamLead(leave.getUser()) && isLeaveStatusPendingByHrExecutive(leave))) {
            model.addAttribute("canDelete", true);
        }
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

        if (isUserTeamLead(sessionUser) && isLeaveStatusPendingByTeamLead(leave)) {
            leave.setLeaveStatus(PENDING_BY_HR_EXECUTIVE);
        } else if (isUserHrExecutive(sessionUser) && isLeaveStatusPendingByHrExecutive(leave)) {
            leave.setLeaveStatus(APPROVED_BY_HR_EXECUTIVE);
        }
    }

    public void updateLeaveStatusToDeny(Leave leave, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (isUserTeamLead(sessionUser) && isLeaveStatusPendingByTeamLead(leave)) {
            leave.setLeaveStatus(DENIED_BY_TEAM_LEAD);
        } else if (isUserHrExecutive(sessionUser) && isLeaveStatusPendingByHrExecutive(leave)) {
            leave.setLeaveStatus(DENIED_BY_HR_EXECUTIVE);
        }
    }

    public void setNewLeaveNotificationByUserDesignation(Leave leave) {
        User user = leave.getUser();

        Notification notification = new Notification();
        notification.setSeen(false);

        if (isUserHrExecutive(user)) {
            notification.setUser(user);
            notification.setMessage("Leave request is added and auto approved");
        } else if (isUserTeamLead(user)) {
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

        if (isUserTeamLead(user)) {
            notification.setMessage("Your leave request is " + status + " by HR");
        } else if (isUserDeveloper(user) || isUserTester(user)) {
            if (isLeaveStatusPendingByHrExecutive(leave)) {
                notification.setMessage("Your leave request is " + status + " by your Team Lead");
            } else if (isLeaveStatusApprovedByHrExecutive(leave)) {
                notification.setMessage("Your leave request is " + status + " by HR");
            }
        }

        notificationService.saveOrUpdate(notification);
    }

    public boolean isUserHrExecutive(User user) {
        return user.getDesignation().equals(HR_EXECUTIVE);
    }

    public boolean isUserTeamLead(User user) {
        return user.getDesignation().equals(TEAM_LEAD);
    }

    public boolean isUserDeveloper(User user) {
        return user.getDesignation().equals(DEVELOPER);
    }

    public boolean isUserTester(User user) {
        return user.getDesignation().equals(TESTER);
    }

    public boolean isLeaveStatusPendingByHrExecutive(Leave leave) {
        return leave.getLeaveStatus().equals(PENDING_BY_HR_EXECUTIVE);
    }

    public boolean isLeaveStatusApprovedByHrExecutive(Leave leave) {
        return leave.getLeaveStatus().equals(APPROVED_BY_HR_EXECUTIVE);
    }

    public boolean isLeaveStatusPendingByTeamLead(Leave leave) {
        return leave.getLeaveStatus().equals(PENDING_BY_TEAM_LEAD);
    }
}
