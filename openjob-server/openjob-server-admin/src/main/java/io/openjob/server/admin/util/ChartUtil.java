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
    public static List<Integer> getDateList(Long beginTime, Long endTime) {
        List<Integer> dayList = new ArrayList<>();
        int days = (int) Math.floor((endTime - beginTime) / TimeUnit.DAYS.toSeconds(1));
        for (int i = 1; i < days + 1; i++) {
            Calendar calendar = new Calendar.Builder().setInstant(endTime * TimeUnit.SECONDS.toMillis(1)).build();
            calendar.add(Calendar.DATE, -i);
            Integer dateTime = DateUtil.formatDateByTimestamp(calendar.getTime().toInstant().getEpochSecond());
            dayList.add(dateTime);
        }
        return dayList;
    }

    public static List<Integer> getHourList(Long timestamp, Integer days) {
        Calendar calendar = new Calendar.Builder().setInstant(timestamp * TimeUnit.SECONDS.toMillis(1)).build();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        Integer dateTime = DateUtil.formatDateByTimestamp(calendar.getTime().toInstant().getEpochSecond());
        return null;
    }
}
