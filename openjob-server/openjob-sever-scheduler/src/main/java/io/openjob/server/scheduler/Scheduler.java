package io.openjob.server.scheduler;

import io.openjob.server.scheduler.timer.SystemTimer;
import io.openjob.server.scheduler.timer.TimerTask;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class Scheduler {
    private final List<SystemTimer> times = new ArrayList<>();

    public void start() {
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Runnable r = () -> {
                String name = "timer" + finalI;
                SystemTimer systemTimer = new SystemTimer(name);
                times.add(systemTimer);

                do {
                    systemTimer.advanceClock(200L);
                    System.out.println(name + " clock");
                } while (true);
            };

            new Thread(r, "name").start();
        }
    }

    @SneakyThrows
    public void add() {
        Thread.sleep(1000L);
        System.out.println(times.size());

        int i = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i).add(new TimerTask(1L, 2L));
        int i2 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i2).add(new TimerTask(1L, 5L));
        int i3 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i3).add(new TimerTask(1L, 5L));
        int i4 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i4).add(new TimerTask(1L, 8L));
        int i5 = ThreadLocalRandom.current().nextInt(times.size());
        times.get(i5).add(new TimerTask(1L, 28L));
    }
}
