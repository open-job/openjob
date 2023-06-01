package io.openjob.server.admin.util;

import io.openjob.common.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class ChartUtilTest {
    @Test
    public void testGetDateList() {
        List<Integer> dateList = ChartUtil.getDateList(DateUtil.timestamp() - TimeUnit.DAYS.toSeconds(5), DateUtil.timestamp());
        Assertions.assertEquals(dateList.size(), 5);
    }

    @Test
    public void testGetHourList() {
        List<Integer> hourList = ChartUtil.getHourList(DateUtil.timestamp() - TimeUnit.DAYS.toSeconds(3), DateUtil.timestamp());
        Assertions.assertEquals(hourList.size(), 3 * 24);
    }
}
