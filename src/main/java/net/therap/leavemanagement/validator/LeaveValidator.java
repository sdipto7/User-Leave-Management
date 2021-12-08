package net.therap.leavemanagement.validator;

import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.LeaveType;
import net.therap.leavemanagement.service.LeaveStatService;
import net.therap.leavemanagement.util.DayCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
        Leave leave = (Leave) target;
        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(leave.getUser().getId());

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
}
