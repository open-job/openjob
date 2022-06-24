package io.openjob.server.scheduler.timer;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class TimerTask implements Runnable {
    protected Long delay = 1L;
    protected TimerTaskEntry timerTaskEntry;

    public TimerTask(Long delay) {
        this.delay = delay;
    }

    public void cancel() {
        synchronized (this) {
            if (Objects.nonNull(timerTaskEntry)) {
                timerTaskEntry.remove();
            }

            timerTaskEntry = null;
        }
    }

    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this) {
            if (Objects.nonNull(timerTaskEntry) && timerTaskEntry != entry) {
            }
            timerTaskEntry = entry;
        }
    }

    @Override
    public void run() {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(sdf.format(date));
    }
}
