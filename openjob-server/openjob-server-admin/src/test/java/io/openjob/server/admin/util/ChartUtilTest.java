package io.openjob.server.admin.util;

import io.openjob.common.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
public class ChartUtilTest {
    @Test
    public void testGetDateList() {
        ChartUtil.getDateList(DateUtil.timestamp() - TimeUnit.DAYS.toSeconds(5), DateUtil.timestamp());
    }
}
