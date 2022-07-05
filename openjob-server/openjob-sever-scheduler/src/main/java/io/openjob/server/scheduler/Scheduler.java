package io.openjob.server.scheduler;

import io.openjob.server.scheduler.timer.SystemTimer;
import io.openjob.server.scheduler.timer.TimerTask;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class Scheduler {
    private static final List<SystemTimer> TIMES = new ArrayList<>();

    private static ThreadPoolExecutor taskExecutor;

    private static final Integer TIME_COUNT = 5;

    /**
     * Start Scheduler.
     */
    public static void start() {
        taskExecutor = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(Integer.MAX_VALUE), r -> new Thread(r, "wheel"));

        for (int i = 0; i < TIME_COUNT; i++) {
            int finalI = i;
            taskExecutor.submit(() -> {
                String name = "timer-" + finalI + "-";
                SystemTimer systemTimer = new SystemTimer(name);
                TIMES.add(systemTimer);

                do {
                    systemTimer.advanceClock(200L);
                    System.out.println(name + " clock");
                } while (true);
            });
        }
    }

    /**
     * Remove task from timing wheel by task ids.
     *
     * @param taskIds taskIds
     */
    public static void removeByTaskId(Set<Long> taskIds) {

    }

    /**
     * Remove task from timing wheel by slots ids.
     *
     * @param slotsIds slotsIds
     */
    public static void removeBySlotsId(Set<Long> slotsIds) {

    }

    /**
     * Add
     */
    @SneakyThrows
    public void add() {
        Thread.sleep(3000L);
        System.out.println(TIMES.size());

        int i = ThreadLocalRandom.current().nextInt(TIMES.size());
        TIMES.get(i).add(new TimerTask(1L, 1L, 2L));
        int i2 = ThreadLocalRandom.current().nextInt(TIMES.size());
        TIMES.get(i2).add(new TimerTask(2L, 1L, 5L));
        int i3 = ThreadLocalRandom.current().nextInt(TIMES.size());
        TIMES.get(i3).add(new TimerTask(3L, 2L, 5L));
        int i4 = ThreadLocalRandom.current().nextInt(TIMES.size());
        TIMES.get(i4).add(new TimerTask(4L, 2L, 8L));
        int i5 = ThreadLocalRandom.current().nextInt(TIMES.size());
        TIMES.get(i5).add(new TimerTask(5L, 3L, 28L));
    }
}
