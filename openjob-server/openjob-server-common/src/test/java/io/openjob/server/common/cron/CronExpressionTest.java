package io.openjob.server.common.cron;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class CronExpressionTest {
    @Test
    public void testExpression() throws ParseException {
        CronExpression cronExpression = new CronExpression("*/30 * * * * ?");
        String cronExpression1 = cronExpression.getCronExpression();
        cronExpression.getNextValidTimeAfter(new Date());
    }
}
