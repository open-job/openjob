package io.openjob.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class DateUtilTest {
    @Test
    public void testFormatDateByTimestamp() {
        Integer dateTime = DateUtil.formatDateByTimestamp(DateUtil.timestamp());
        Integer HourTime = DateUtil.formatHourByTimestamp(DateUtil.timestamp());

        Assertions.assertNotNull(dateTime);
        Assertions.assertNotNull(HourTime);
    }

    @Test
    public void testGetZeroTimestamp() throws ParseException {
        Long zeroTimestamp = DateUtil.getZeroTimestamp();

        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = dateFormat.format(today);
        Date todayZero = dateFormat.parse(todayStr);
        Long todayZeroTime = todayZero.toInstant().getEpochSecond();
        Assertions.assertEquals(zeroTimestamp, todayZeroTime);
    }

    @Test
    public void testGetNowFormatDate() {
        Integer nowFormatDate = DateUtil.getNowFormatDate();
        Assertions.assertNotNull(nowFormatDate);
    }
}
