package io.openjob.server.scheduler.scheduler;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayDeleteListScheduler extends AbstractDelayScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentDeleteListSlots();
        // Not slots on current node.
        if (CollectionUtils.isEmpty(slots)) {
            return;
        }

        int maxSize = slots.size();
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
        // Not slots on current node.
        if (Objects.isNull(executorService)) {
            return;
        }

        this.executorService.shutdown();
        log.info("Delete List delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentDeleteListSlots();
        this.refreshSlots(slots, DelayDeleteListScheduler.DeleteListRunnable::new);

        log.info("Refresh delete list delay instance slots{}", slots);
    }

    static class DeleteListRunnable extends AbstractRunnable {

        private final DelayInstanceDAO delayInstanceDAO;

        public DeleteListRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.delayInstanceDAO = OpenjobSpringContext.getBean(DelayInstanceDAO.class);
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getDeleteListKey(this.currentSlotId);
            log.info("Delete list delay instance begin！slotId={}", this.currentSlotId);

            while (!finish.get()) {
                try {
                    this.batchDelete(key);
                } catch (InterruptedException interruptedException) {
                    log.info("Delete list delay instance interrupted complete！slotId={}", this.currentSlotId);
                } catch (BeanCreationNotAllowedException beanCreationNotAllowedException) {
                    // Fixed executor shutdown error.
                    log.info("Delete list delay instance destroy complete！slotId={}", this.currentSlotId);
                    break;
                } catch (Throwable ex) {
                    log.error("Delete list delay instance failed!", ex);
                    this.failSleep();
                }
            }

            log.info("Delete list delay instance complete！slotId={}", this.currentSlotId);
        }

        public void batchDelete(String key) throws InterruptedException {
            // Cache list.
            List<Object> popObjects = RedisUtil.popAndRemoveFromList(key, 100);
            if (CollectionUtils.isEmpty(popObjects)) {
                Thread.sleep(500);
                return;
            }

            // Batch delete.
            this.delayInstanceDAO.deleteByTaskIds(popObjects.stream().map(String::valueOf).collect(Collectors.toList()));
        }
    }
}
