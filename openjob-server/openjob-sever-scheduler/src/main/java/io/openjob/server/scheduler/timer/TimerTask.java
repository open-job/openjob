package io.openjob.server.scheduler.timer;

import io.openjob.common.request.ServerSubmitJobInstanceRequest;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Log4j2
public abstract class TimerTask implements Runnable {
    protected TimerTaskEntry timerTaskEntry;

    /**
     * Job instance id.
     */
    protected Long taskId;

    /**
     * Slots id.
     */
    protected Long slotsId;

    /**
     * Expiration.
     */
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
            if (Objects.nonNull(timerTaskEntry) && timerTaskEntry != entry) {
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


}
