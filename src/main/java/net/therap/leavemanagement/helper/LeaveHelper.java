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

        if ((sessionUser.isTeamLead() && leave.isPendingByTeamLead())
                || (sessionUser.isHrExecutive() && leave.isPendingByHrExecutive())) {
            model.addAttribute("canReview", true);
        }

        if (((sessionUser.isDeveloper() || sessionUser.isTester()) && leave.isPendingByTeamLead())
                || (sessionUser.isTeamLead() && leave.getUser().isTeamLead() && leave.isPendingByHrExecutive())) {
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

        if (sessionUser.isTeamLead() && leave.isPendingByTeamLead()) {
            leave.setLeaveStatus(PENDING_BY_HR_EXECUTIVE);
        } else if (sessionUser.isHrExecutive() && leave.isPendingByHrExecutive()) {
            leave.setLeaveStatus(APPROVED_BY_HR_EXECUTIVE);
        }
    }

    public void updateLeaveStatusToDeny(Leave leave, HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (sessionUser.isTeamLead() && leave.isPendingByTeamLead()) {
            leave.setLeaveStatus(DENIED_BY_TEAM_LEAD);
        } else if (sessionUser.isHrExecutive() && leave.isPendingByHrExecutive()) {
            leave.setLeaveStatus(DENIED_BY_HR_EXECUTIVE);
        }
    }

    public void setNewLeaveNotificationByUserDesignation(Leave leave) {
        User user = leave.getUser();

        Notification notification = new Notification();
        notification.setSeen(false);

        if (user.isHrExecutive()) {
            notification.setUser(user);
            notification.setMessage("Leave request is added and auto approved");
        } else if (user.isTeamLead()) {
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

        if (user.isTeamLead()) {
            notification.setMessage("Your leave request is " + status + " by HR");
        } else if (user.isDeveloper() || user.isTester()) {
            if (leave.isPendingByHrExecutive()) {
                notification.setMessage("Your leave request is " + status + " by your Team Lead");
            } else if (leave.isApprovedByHrExecutive()) {
                notification.setMessage("Your leave request is " + status + " by HR");
            }
        }

        notificationService.saveOrUpdate(notification);
    }
}
