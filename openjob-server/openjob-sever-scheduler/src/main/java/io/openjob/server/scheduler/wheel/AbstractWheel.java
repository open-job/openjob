package io.openjob.server.scheduler.wheel;

import io.openjob.common.util.RuntimeUtil;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.scheduler.constant.TimerConstant;
import io.openjob.server.scheduler.timer.SystemTimer;
import io.openjob.server.scheduler.timer.TimerTask;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public abstract class AbstractWheel implements Wheel {
    /**
     * System timers.
     */
    private final List<SystemTimer> systemTimers = new ArrayList<>();

    /**
     * Timer thread pool.
     */
    private ThreadPoolExecutor taskExecutor;

    @Override
    public void stop() {
        // Shutdown task thread pool.
        this.systemTimers.forEach(SystemTimer::shuntDown);
        log.info("Scheduler system timer shutdown!");

        // Shutdown scheduler thread pool.
        taskExecutor.shutdown();
        log.info("system timer thread pool shutdown!");
    }

    @Override
    public void addTimerTask(List<TimerTask> timerTasks) {
        timerTasks.forEach(t -> getSystemTimer(t.getSlotsId()).add(t));
    }

    @Override
    public void removeByTaskId(List<JobInstance> jobInstances) {
        jobInstances.forEach(j -> getSystemTimer(j.getSlotsId()).removeByTaskId(j.getId()));
    }

    @Override
    public void removeBySlotsId(Set<Long> slotsIds) {
        slotsIds.forEach(id -> getSystemTimer(id).removeBySlotsId(id));
    }

    public SystemTimer getSystemTimer(Long slotsId) {
        int size = this.systemTimers.size() - 1;
        int index = (int) (slotsId % size);
        return this.systemTimers.get(index);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    protected void createWheel(int wheelSize, String wheelName) {
        LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(Integer.MAX_VALUE);
        this.taskExecutor = new ThreadPoolExecutor(wheelSize, wheelSize, 0L, TimeUnit.MILLISECONDS, queue, r -> new Thread(r, wheelName));

        for (int i = 0; i < wheelSize; i++) {
            int index = i;
            this.taskExecutor.submit(() -> {
                String name = String.format("%s-%d", TimerConstant.TIMER_THREAD_NAME_PREFIX, index);
                SystemTimer systemTimer = new SystemTimer(name);
                this.systemTimers.add(systemTimer);

                log.info("Scheduler {} is stared!", name);

                // Advance clock.
                while (true) {
                    systemTimer.advanceClock(TimerConstant.TIMER_CLOCK_TIME);
                }
            });
        }
    }
}
