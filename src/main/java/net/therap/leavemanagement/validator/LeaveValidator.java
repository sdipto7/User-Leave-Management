package net.therap.leavemanagement.validator;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.LeaveType;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.service.LeaveStatService;
import net.therap.leavemanagement.util.DayCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static net.therap.leavemanagement.domain.Designation.*;
import static net.therap.leavemanagement.domain.LeaveStatus.*;

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
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession(true);

        Leave leave = (Leave) target;

        validateLeaveLimit(leave, errors);

        if (Objects.nonNull(request.getParameter("action_approve")) ||
                Objects.nonNull(request.getParameter("action_reject"))) {
            validateLeaveStatus(leave, session, errors);
        } else if (Objects.nonNull(request.getParameter("action_delete"))) {
            validateLeaveDelete(leave, session, errors);
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

    public void validateLeaveStatus(Leave leave, HttpSession session, Errors errors) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (leave.getLeaveStatus().equals(APPROVED_BY_HR_EXECUTIVE)
                || leave.getLeaveStatus().equals(DENIED_BY_HR_EXECUTIVE)) {
            errors.reject("validation.leave.leaveStatus.reviewDone");
        } else if (sessionUser.getDesignation().equals(HR_EXECUTIVE) &&
                !leave.getLeaveStatus().equals(PENDING_BY_HR_EXECUTIVE)) {
            errors.reject("validation.leave.leaveStatus.hrRestriction");
        } else if (sessionUser.getDesignation().equals(TEAM_LEAD) &&
                !leave.getLeaveStatus().equals(PENDING_BY_TEAM_LEAD)) {
            errors.reject("validation.leave.leaveStatus.teamLeadRestriction");
        }
    }

    public void validateLeaveDelete(Leave leave, HttpSession session, Errors errors) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (sessionUser.getDesignation().equals(TEAM_LEAD) &&
                !leave.getLeaveStatus().equals(PENDING_BY_HR_EXECUTIVE)) {
            errors.reject("validation.leave.leaveStatus.deleteByTeamLead");
        } else if ((sessionUser.getDesignation().equals(DEVELOPER) ||
                sessionUser.getDesignation().equals(TESTER)) &&
                !leave.getLeaveStatus().equals(PENDING_BY_TEAM_LEAD)) {
            errors.reject("validation.leave.leaveStatus.deleteByOther");
        }
    }
}
