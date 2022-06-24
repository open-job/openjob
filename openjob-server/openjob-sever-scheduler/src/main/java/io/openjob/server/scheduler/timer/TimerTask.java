package io.openjob.server.scheduler.timer;

import lombok.Data;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class TimerTask implements Runnable {
    protected Long delay = 1L;
    protected TimerTaskEntry timerTaskEntry;

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
        System.out.println("");
    }
}
