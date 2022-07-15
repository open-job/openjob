package io.openjob.server.scheduler.timer;

import io.openjob.common.util.DateUtil;
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
    protected TimerTaskEntry timerTaskEntry;
    protected Long taskId;
    protected Long slotsId;
    protected Long expiration;

    /**
     * Timer task.
     *
     * @param taskId     taskId
     * @param slotsId    slotsId
     * @param expiration expiration
     */
    public TimerTask(Long taskId, Long slotsId, Long expiration) {
        this.expiration = expiration;
        this.slotsId = slotsId;
        this.taskId = taskId;
    }

    /**
     * Cancel timer task.
     */
    public void cancel() {
        synchronized (this) {
            if (Objects.nonNull(timerTaskEntry)) {
                timerTaskEntry.remove();
            }

            timerTaskEntry = null;
        }
    }

    /**
     * Set time task entry.
     *
     * @param entry entry
     */
    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this) {
            if (Objects.nonNull(timerTaskEntry) && Objects.nonNull(timerTaskEntry)) {
                timerTaskEntry.remove();
            }

            timerTaskEntry = entry;
        }
    }

    /**
     * Getter for task id.
     *
     * @return Long
     */
    public Long getTaskId() {
        return taskId;
    }

    @Override
    public void run() {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        log.info("do task time=" + sdf.format(date) + Thread.currentThread().getName() + " id=" + taskId + " now=" + DateUtil.now() + " expiration=" + this.getExpiration());
    }
}
