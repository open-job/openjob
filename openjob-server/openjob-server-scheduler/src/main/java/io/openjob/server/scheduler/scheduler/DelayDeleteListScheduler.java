package io.openjob.server.scheduler.scheduler;

import io.openjob.server.scheduler.util.DelaySlotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayDeleteListScheduler extends AbstractDelayScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentDeleteListSlots();
        int maxSize = slots.size() > 0 ? slots.size() : 1;

        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                maxSize,
                maxSize,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(maxSize),
                r -> new Thread(r, String.format("delay-delete-list-%s", threadId.getAndIncrement()))
        );

        slots.forEach(s -> {
            DelayDeleteListScheduler.DeleteListRunnable listRunnable = new DelayDeleteListScheduler.DeleteListRunnable(s);
            runnableList.put(s, listRunnable);
            executorService.submit(listRunnable);
        });

        executorService.allowCoreThreadTimeOut(true);
        log.info("Delete list delay instance slots{}", slots);
    }

    @Override
    public void stop() {
        this.executorService.shutdownNow();
        log.info("Delete List delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentDeleteListSlots();
        this.refreshSlots(slots, DelayDeleteListScheduler.DeleteListRunnable::new);

        log.info("Refresh delete list delay instance slots{}", slots);
    }

    static class DeleteListRunnable extends AbstractRunnable {
        public DeleteListRunnable(Long currentSlotId) {
            super(currentSlotId);
        }

        @Override
        public void run() {

        }
    }
}
