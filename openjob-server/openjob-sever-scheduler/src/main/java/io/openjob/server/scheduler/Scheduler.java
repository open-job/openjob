package io.openjob.server.scheduler;

import io.openjob.common.util.RuntimeUtil;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.scheduler.constant.TimerConstant;
import io.openjob.server.scheduler.timer.SystemTimer;
import io.openjob.server.scheduler.timer.TimerTask;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
public class Scheduler {
    /**
     * System timers.
     */
    private static final List<SystemTimer> SYSTEM_TIMERS = new ArrayList<>();

    /**
     * Timer thread pool.
     */
    private static ThreadPoolExecutor taskExecutor;

    /**
     * Start Scheduler.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public static void start() {
        int coreCpu = RuntimeUtil.processors();
        taskExecutor = new ThreadPoolExecutor(coreCpu, coreCpu, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(Integer.MAX_VALUE), r -> new Thread(r, "wheel"));

        for (int i = 0; i < coreCpu; i++) {
            int index = i;
            taskExecutor.submit(() -> {
                String name = String.format("%s-%d", TimerConstant.TIMER_THREAD_NAME_PREFIX, index);
                SystemTimer systemTimer = new SystemTimer(name);
                SYSTEM_TIMERS.add(systemTimer);

                log.info("Scheduler {} is stared!", name);

                // Advance clock.
                while (true) {
                    systemTimer.advanceClock(TimerConstant.TIMER_CLOCK_TIME);
                }
            });
        }
    }

    /**
     * Stop scheduler.
     */
    public static void stop() {
        // Shutdown task thread pool.
        SYSTEM_TIMERS.forEach(SystemTimer::shuntDown);
        log.info("Scheduler system timer shutdown!");

        // Shutdown scheduler thread pool.
        taskExecutor.shutdown();
        log.info("system timer thread pool shutdown!");
    }

    /**
     * Add timer task.
     *
     * @param timerTasks timerTasks
     */
    public static void addTimerTask(List<TimerTask> timerTasks) {
        timerTasks.forEach(t -> getSystemTimer(t.getSlotsId()).add(t));
    }

    /**
     * Remove task from timing wheel by task ids.
     *
     * @param jobInstances jobInstances
     */
    public static void removeByTaskId(List<JobInstance> jobInstances) {
        jobInstances.forEach(j -> getSystemTimer(j.getJobSlotsId()).removeByTaskId(j.getId()));
    }

    /**
     * Remove task from timing wheel by slots ids.
     *
     * @param slotsIds slotsIds
     */
    public static void removeBySlotsId(Set<Long> slotsIds) {
        slotsIds.forEach(id -> getSystemTimer(id).removeBySlotsId(id));
    }

    public static SystemTimer getSystemTimer(Long slotsId) {
        int size = SYSTEM_TIMERS.size() - 1;
        int index = (int) (slotsId % size);
        return SYSTEM_TIMERS.get(index);
    }
}
