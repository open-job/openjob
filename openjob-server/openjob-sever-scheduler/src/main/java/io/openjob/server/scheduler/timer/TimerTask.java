package io.openjob.server.scheduler.timer;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Log4j2
public class TimerTask implements Runnable {
    protected Long delay = 1L;
    protected TimerTaskEntry timerTaskEntry;
    protected Long taskId;
    protected Long slotsId;

    public TimerTask(Long taskId, Long slotsId, Long delay) {
        this.delay = delay;
        this.slotsId = slotsId;
        this.taskId = taskId;
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

    public Long getTaskId() {
        return taskId;
    }

    @Override
    public void run() {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        log.info("do task" + sdf.format(date) + Thread.currentThread().getName() + " id=" + taskId);
    }
}
