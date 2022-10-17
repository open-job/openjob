package io.openjob.server.scheduler.wheel;

import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.scheduler.timer.TimerTask;

import java.util.List;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface Wheel {
    void start();

    void stop();

    /**
     * Add timer task.
     *
     * @param timerTasks timerTasks
     */
    void addTimerTask(List<TimerTask> timerTasks);

    /**
     * Remove task from timing wheel by task ids.
     *
     * @param jobInstances jobInstances
     */
    void removeByTaskId(List<JobInstance> jobInstances);

    /**
     * Remove task from timing wheel by slots ids.
     *
     * @param slotsIds slotsIds
     */
    void removeBySlotsId(Set<Long> slotsIds);
}
