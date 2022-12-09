package io.openjob.server.scheduler.timer;

import io.openjob.common.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SchedulerTest {
    @Test
    public void testSchedulerByTaskId() {
        SystemTimer systemTimer = new SystemTimer("timer");
        long now = DateUtil.timestamp();

        systemTimer.add(new SchedulerTimerTask(2L, 1L, now + 5L));
        systemTimer.add(new SchedulerTimerTask(3L, 1L, now + 10L));
        systemTimer.add(new SchedulerTimerTask(4L, 1L, now + 40L));
        systemTimer.add(new SchedulerTimerTask(5L, 1L, now + 55L));

        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(sdf.format(date));

//        systemTimer.removeByTaskId(3L);
//        systemTimer.removeByTaskId(4L);
//        systemTimer.removeByTaskId(5L);

//        do {
//            System.out.println(systemTimer);
//            systemTimer.advanceClock(800L);
//            System.out.println(systemTimer.size());
//        } while (true);
    }

    @Test
    public void testSchedulerBySlotsId() {
        long now = DateUtil.timestamp();


        SystemTimer systemTimer = new SystemTimer("timer");
        systemTimer.add(new SchedulerTimerTask(1L, 1L, now + 2L));
        systemTimer.add(new SchedulerTimerTask(2L, 1L, now + 5L));
        systemTimer.add(new SchedulerTimerTask(3L, 3L, now + 5L));
        systemTimer.add(new SchedulerTimerTask(4L, 3L, now + 8L));
        systemTimer.add(new SchedulerTimerTask(5L, 3L, now + 28L));

        systemTimer.removeBySlotsId(3L);
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(sdf.format(date));
    }
}
