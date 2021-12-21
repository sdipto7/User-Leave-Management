package net.therap.leavemanagement.util;

import net.therap.leavemanagement.domain.Day;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author rumi.dipto
 * @since 12/6/21
 */
public class DateTimeUtil {

    public static int getLeaveDayCount(Date startDate, Date endDate) {
        int dayCount = getDayCount(startDate, endDate) + 1;

        String startDay = new SimpleDateFormat("EEEE").format(startDate);
        String endDay = new SimpleDateFormat("EEEE").format(endDate);

        if (Day.SUN.getNaturalName().equals(startDay) || Day.THU.getNaturalName().equals(endDay)) {
            dayCount += 2;
        }

        return dayCount;
    }

    public static int getDayCount(Date startDate, Date endDate) {
        long timeDifference = endDate.getTime() - startDate.getTime();
        int dayCount = (int) TimeUnit.DAYS.convert(timeDifference, TimeUnit.MILLISECONDS);

        return dayCount;
    }
}
