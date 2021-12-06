package net.therap.leavemanagement.helper;

import net.therap.leavemanagement.domain.Day;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
@Component
public class DateHelper {

    public int getLeaveDayCount(Date startDate, Date endDate) {
        long timeDifference = endDate.getTime() - startDate.getTime();
        int dayCount = (int) TimeUnit.DAYS.convert(timeDifference, TimeUnit.MILLISECONDS);

        String startDay = getDayInWeek(startDate);
        String endDay = getDayInWeek(endDate);

        if (Day.SUN.getNaturalName().equals(startDay) || Day.THU.getNaturalName().equals(endDay)) {
            dayCount += 2;
        }

        return dayCount;
    }

    private String getDayInWeek(Date date) {
        return new SimpleDateFormat("EEEE").format(date);
    }
}
