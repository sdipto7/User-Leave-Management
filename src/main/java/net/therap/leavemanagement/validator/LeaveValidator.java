package net.therap.leavemanagement.validator;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.LeaveType;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.LeaveStatService;
import net.therap.leavemanagement.util.DayCounter;
import net.therap.leavemanagement.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
@Component
public class LeaveValidator implements Validator {

    @Autowired
    private LeaveStatService leaveStatService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Leave.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HttpServletRequest request = ServletUtil.getHttpServletRequest();
        User sessionUser = (User) WebUtils.getSessionAttribute(ServletUtil.getHttpServletRequest(), "SESSION_USER");

        Leave leave = (Leave) target;

        validateLeaveLimit(leave, errors);

        if (Objects.nonNull(request.getParameter("action_approve")) ||
                Objects.nonNull(request.getParameter("action_reject"))) {
            validateLeaveStatus(leave, sessionUser, errors);
        } else if (Objects.nonNull(request.getParameter("action_delete"))) {
            validateLeaveDelete(leave, sessionUser, errors);
        }
    }

    public void validateLeaveLimit(Leave leave, Errors errors) {
        User user = leave.getUser();
        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(user.getId());

        if (Objects.nonNull(leave.getStartDate()) && Objects.nonNull(leave.getEndDate())) {
            int dayCount = DayCounter.getDayCount(leave.getStartDate(), leave.getEndDate());
            int leaveRequestDayCount = DayCounter.getLeaveDayCount(leave.getStartDate(), leave.getEndDate());
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

    public void validateLeaveStatus(Leave leave, User sessionUser, Errors errors) {
        if (leave.isApprovedByHrExecutive() || leave.isDeniedByHrExecutive()) {
            errors.reject("validation.leave.leaveStatus.reviewDone");
        } else if (sessionUser.isHrExecutive() && !(leave.isPendingByHrExecutive())) {
            errors.reject("validation.leave.leaveStatus.hrRestriction");
        } else if (sessionUser.isTeamLead() && !(leave.isPendingByTeamLead())) {
            errors.reject("validation.leave.leaveStatus.teamLeadRestriction");
        }
    }

    public void validateLeaveDelete(Leave leave, User sessionUser, Errors errors) {
        if (sessionUser.isTeamLead() && !(leave.isPendingByHrExecutive())) {
            errors.reject("validation.leave.leaveStatus.deleteByTeamLead");
        } else if ((sessionUser.isDeveloper() || sessionUser.isTester())
                && !(leave.isPendingByTeamLead())) {
            errors.reject("validation.leave.leaveStatus.deleteByOther");
        }
    }
}
