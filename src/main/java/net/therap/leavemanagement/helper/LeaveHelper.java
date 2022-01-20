package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.*;
import net.therap.leavemanagement.service.LeaveStatService;
import net.therap.leavemanagement.service.NotificationService;
import net.therap.leavemanagement.service.UserManagementService;
import net.therap.leavemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static net.therap.leavemanagement.domain.Designation.*;
import static net.therap.leavemanagement.domain.LeaveStatus.*;
import static net.therap.leavemanagement.util.Constant.pageSize;

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
    private LeaveStatService leaveStatService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @Autowired
    private UserHelper userHelper;

    public void checkAccessByUserDesignation(User user, HttpSession session, ModelMap modelMap) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (sessionUser.getDesignation().equals(user.getDesignation())) {
            authorizationHelper.checkAccess(user);
        } else if (sessionUser.getDesignation().equals(TEAM_LEAD) &&
                (user.getDesignation().equals(DEVELOPER) ||
                        user.getDesignation().equals(TESTER))) {

            userHelper.checkAndAddAuthorizedTeamLeadIfExist(user, session, modelMap);
        }
    }

    public int getTotalPageNumber(int listSize) {
        return ((listSize % pageSize == 0) ? (listSize / pageSize) : (listSize / pageSize) + 1);
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

    public void setDataForLeaveSaveForm(User user, ModelMap modelMap) {
        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(user.getId());

        modelMap.addAttribute("leaveTypeList", Arrays.asList(LeaveType.values()));
        modelMap.addAttribute("casualLeaveCount", 15 - leaveStat.getCasualLeaveCount());
        modelMap.addAttribute("sickLeaveCount", 15 - leaveStat.getSickLeaveCount());
    }

    public void setConditionalDataForLeaveDetailsView(Leave leave, HttpSession session, ModelMap modelMap) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if ((sessionUser.isTeamLead() && leave.isPendingByTeamLead())
                || (sessionUser.isHrExecutive() && leave.isPendingByHrExecutive())) {
            modelMap.addAttribute("canReview", true);
        }

        if (((sessionUser.isDeveloper() || sessionUser.isTester()) && leave.isPendingByTeamLead())
                || (sessionUser.isTeamLead() && leave.getUser().isTeamLead() && leave.isPendingByHrExecutive())) {
            modelMap.addAttribute("canDelete", true);
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
            if (leave.isPendingByHrExecutive() || leave.isDeniedByTeamLead()) {
                notification.setMessage("Your leave request is " + status + " by your Team Lead");
            } else if (leave.isApprovedByHrExecutive() || leave.isDeniedByHrExecutive()) {
                notification.setMessage("Your leave request is " + status + " by HR");
            }
        }

        notificationService.saveOrUpdate(notification);
    }
}
