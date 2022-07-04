package io.openjob.server.scheduler;

import io.openjob.server.scheduler.timer.SystemTimer;
import io.openjob.server.scheduler.timer.TimerTask;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
    private final List<SystemTimer> times = new ArrayList<>();

    private ThreadPoolExecutor taskExecutor;

    public void start() {
        taskExecutor = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(Integer.MAX_VALUE), r -> new Thread(r, "wheel"));

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            taskExecutor.submit(() -> {
                String name = "timer-" + finalI + "-";
                SystemTimer systemTimer = new SystemTimer(name);
                times.add(systemTimer);

                do {
                    systemTimer.advanceClock(200L);
                    System.out.println(name + " clock");
                } while (true);
            });
        }
    }

    @SneakyThrows
    public void add() {
        Thread.sleep(3000L);
        System.out.println(times.size());

        int i = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i).add(new TimerTask(1L, 2L));
        int i2 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i2).add(new TimerTask(2L, 5L));
        int i3 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i3).add(new TimerTask(3L, 5L));
        int i4 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i4).add(new TimerTask(4L, 8L));
        int i5 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i5).add(new TimerTask(5L, 28L));
    }
}
