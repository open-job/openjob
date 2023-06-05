package io.openjob.server.admin.util;

import io.openjob.common.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class ChartUtil {

    /**
     * Get date list
     *
     * @param beginTime beginTime
     * @param endTime   endTime
     * @return List
     */
    public static List<Integer> getDateList(Long beginTime, Long endTime) {
        // Days
        int days = (int) Math.floor((endTime - beginTime) * 1.0 / TimeUnit.DAYS.toSeconds(1));

        // Date list
        List<Integer> dateList = new ArrayList<>();
        for (int i = 0; i < days + 1; i++) {
            Calendar calendar = new Calendar.Builder().setInstant(endTime * TimeUnit.SECONDS.toMillis(1)).build();
            calendar.add(Calendar.DATE, -i);
            Integer dateTime = DateUtil.formatDateByTimestamp(calendar.getTime().toInstant().getEpochSecond());
            dateList.add(dateTime);
        }

        // Sort
        dateList.sort(Integer::compareTo);
        return dateList;
    }

    /**
     * Get hour list
     *
     * @param beginTime beginTime
     * @param endTime   endTime
     * @return List
     */
    public static List<Integer> getHourList(Long beginTime, Long endTime) {
        // Hour
        int hours = (int) Math.floor((endTime - beginTime) * 1.0 / TimeUnit.HOURS.toSeconds(1));

        // Hour list
        List<Integer> hourList = new ArrayList<>();
        for (int i = 0; i < hours + 1; i++) {
            Calendar calendar = new Calendar.Builder().setInstant(endTime * TimeUnit.SECONDS.toMillis(1)).build();
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - i);
            Integer hourTime = DateUtil.formatHourByTimestamp(calendar.getTime().toInstant().getEpochSecond());
            hourList.add(hourTime);
        }

        // Sort
        hourList.sort(Integer::compareTo);
        return hourList;
    }
}
