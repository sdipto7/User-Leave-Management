package net.therap.leavemanagement.validator;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.LeaveType;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.LeaveService;
import net.therap.leavemanagement.service.LeaveStatService;
import net.therap.leavemanagement.util.DateTimeUtil;
import net.therap.leavemanagement.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
@Component
public class LeaveValidator implements Validator {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveStatService leaveStatService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Leave.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Leave leave = (Leave) target;

        validateLeaveLimit(leave, errors);

        validateStartAndEndDate(leave, errors);

        validateOverlappingOfLeaveDuration(leave, errors);

        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        if (Objects.nonNull(request.getParameter("action_approve")) ||
                Objects.nonNull(request.getParameter("action_reject"))) {
            validateLeaveStatus(leave, errors);
        } else if (Objects.nonNull(request.getParameter("action_delete"))) {
            validateLeaveDelete(leave, errors);
        }
    }

    public void validateStartAndEndDate(Leave leave, Errors errors) {
        User sessionUser = (User) WebUtils.getSessionAttribute(ServletUtil.getHttpServletRequest(), "SESSION_USER");
        List<Leave> pendingLeaveList = leaveService.findPendingLeavesOfUser(sessionUser.getId());

        Date startDate = leave.getStartDate();
        Date endDate = leave.getEndDate();

        boolean isStartDateSame = pendingLeaveList.stream()
                .anyMatch(pendingLeave -> pendingLeave.getStartDate().compareTo(startDate) == 0);
        boolean isEndDateSame = pendingLeaveList.stream()
                .anyMatch(pendingLeave -> pendingLeave.getEndDate().compareTo(endDate) == 0);

        if (isStartDateSame) {
            errors.rejectValue("startDate", "validation.leave.duplicate.startDate");
        } else if (isEndDateSame) {
            errors.rejectValue("endDate", "validation.leave.duplicate.endDate");
        }
    }

    public void validateOverlappingOfLeaveDuration(Leave leave, Errors errors) {
        User sessionUser = (User) WebUtils.getSessionAttribute(ServletUtil.getHttpServletRequest(), "SESSION_USER");
        List<Leave> pendingLeaveList = leaveService.findPendingLeavesOfUser(sessionUser.getId());

        Date startDate = leave.getStartDate();
        Date endDate = leave.getEndDate();

        boolean isStartDateOverlapping = pendingLeaveList.stream()
                .anyMatch(pendingLeave ->
                        (startDate.after(pendingLeave.getStartDate()) && startDate.before(pendingLeave.getEndDate())));
        boolean isEndDateOverlapping = pendingLeaveList.stream()
                .anyMatch(pendingLeave ->
                        (endDate.after(pendingLeave.getStartDate()) && endDate.before(pendingLeave.getEndDate())));

        if (isStartDateOverlapping) {
            errors.rejectValue("startDate", "validation.leave.overlapping.startDate");
        } else if (isEndDateOverlapping) {
            errors.rejectValue("endDate", "validation.leave.overlapping.endDate");
        }
    }

    public void validateLeaveLimit(Leave leave, Errors errors) {
        User user = leave.getUser();
        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(user.getId());

        if (Objects.nonNull(leave.getStartDate()) && Objects.nonNull(leave.getEndDate())) {
            int dayCount = DateTimeUtil.getDayCount(leave.getStartDate(), leave.getEndDate());
            int leaveRequestDayCount = DateTimeUtil.getLeaveDayCount(leave.getStartDate(), leave.getEndDate());
            int leaveTakenCount = 0;

            if (dayCount < 0) {
                errors.rejectValue("endDate", "validation.leave.behindDate");
            }

            if (leave.getLeaveType().equals(LeaveType.CASUAL)) {
                leaveTakenCount = leaveStat.getCasualLeaveCount();
            } else {
                leaveTakenCount = leaveStat.getSickLeaveCount();
            }

            if ((leaveTakenCount + leaveRequestDayCount) > 15) {
                errors.rejectValue("endDate", "validation.leave.dayLimitCrossed");
            }
        }
    }

    public void validateLeaveStatus(Leave leave, Errors errors) {
        User sessionUser = (User) WebUtils.getSessionAttribute(ServletUtil.getHttpServletRequest(), "SESSION_USER");

        if (leave.isApprovedByHrExecutive() || leave.isDeniedByHrExecutive()) {
            errors.reject("validation.leave.leaveStatus.reviewDone");
        } else if (sessionUser.isHrExecutive() && !(leave.isPendingByHrExecutive())) {
            errors.reject("validation.leave.leaveStatus.hrRestriction");
        } else if (sessionUser.isTeamLead() && !(leave.isPendingByTeamLead())) {
            errors.reject("validation.leave.leaveStatus.teamLeadRestriction");
        }
    }

    public void validateLeaveDelete(Leave leave, Errors errors) {
        User sessionUser = (User) WebUtils.getSessionAttribute(ServletUtil.getHttpServletRequest(), "SESSION_USER");

        if (sessionUser.isTeamLead() && !(leave.isPendingByHrExecutive())) {
            errors.reject("validation.leave.leaveStatus.deleteByTeamLead");
        } else if ((sessionUser.isDeveloper() || sessionUser.isTester())
                && !(leave.isPendingByTeamLead())) {
            errors.reject("validation.leave.leaveStatus.deleteByOther");
        }
    }
}
