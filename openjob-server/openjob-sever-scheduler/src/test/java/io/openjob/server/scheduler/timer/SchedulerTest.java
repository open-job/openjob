package io.openjob.server.scheduler.timer;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SchedulerTest {
    @Test
    public void testScheduler() {
        SystemTimer systemTimer = new SystemTimer("timer");

        systemTimer.add(new TimerTask(2L));
        systemTimer.add(new TimerTask(5L));
        systemTimer.add(new TimerTask(5L));
        systemTimer.add(new TimerTask(8L));
        systemTimer.add(new TimerTask(28L));

        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(sdf.format(date));

        do {
            systemTimer.advanceClock(500L);
            System.out.println(systemTimer.size());
        } while (true);
    }
}
