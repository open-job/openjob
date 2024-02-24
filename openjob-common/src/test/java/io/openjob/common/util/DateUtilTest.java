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
        Integer dateTime = DateUtil.formatDateByTimestamp(DateUtil.timestamp()* 1000);
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
        Assertions.assertNotNull(zeroTimestamp);
        Assertions.assertNotNull(todayZeroTime);
    }

    @Test
    public void testFormatTimestamp() {
        String str = DateUtil.formatTimestamp(1685624782010L);
        Assertions.assertNotNull(str);
    }

    @Test
    public void testGetNowFormatDate() {
        Integer nowFormatDate = DateUtil.getNowFormatDate();
        Assertions.assertNotNull(nowFormatDate);
    }

    @Test
    public void testFormatDate() {
        String formatDate = DateUtil.formatDatePattern("20230601");
        Assertions.assertEquals(formatDate, "2023-06-01");
    }

    @Test
    public void testFormatHourPattern() {
        String formatDate = DateUtil.formatHourPattern("2023060106");
        Assertions.assertEquals(formatDate, "2023-06-01 06:00");
    }
}
