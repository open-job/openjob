package io.openjob.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
