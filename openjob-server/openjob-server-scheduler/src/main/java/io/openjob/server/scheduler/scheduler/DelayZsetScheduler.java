package io.openjob.server.scheduler.scheduler;

import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Component
public class DelayZsetScheduler extends AbstractDelayZsetScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentZsetSlots();
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
                r -> new Thread(r, String.format("delay-zset-%s", threadId.getAndIncrement()))
        );

        slots.forEach(s -> {
            ZsetRunnable zsetRunnable = new ZsetRunnable(s);
            runnableList.put(s, zsetRunnable);
            executorService.submit(zsetRunnable);
        });

        executorService.allowCoreThreadTimeOut(true);
        log.info("Range Delay instance slots{}", slots);
    }

    @Override
    public void stop() {
        // Not slots on current node.
        if (Objects.isNull(executorService)) {
            return;
        }

        this.executorService.shutdown();
        log.info("Range delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentZsetSlots();
        this.refreshSlots(slots, ZsetRunnable::new);

        log.info("Refresh range delay instance slots{}", slots);
    }

    static class ZsetRunnable extends AbstractZsetRunnable {

        /**
         * New ZsetRunnable.
         *
         * @param currentSlotId current slot id.
         */
        public ZsetRunnable(Long currentSlotId) {
            super(currentSlotId);
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getZsetKey(this.currentSlotId);
            log.info("Range delay instance begin！slotId={}", this.currentSlotId);

            while (!finish.get()) {
                try {
                    this.rangeDelayInstance(key);
                } catch (InterruptedException interruptedException) {
                    log.info("Range delay instance interrupted complete！slotId={}", this.currentSlotId);
                } catch (BeanCreationNotAllowedException beanCreationNotAllowedException) {
                    // Fixed executor shutdown error.
                    log.info("Range delay instance destroy complete！slotId={}", this.currentSlotId);
                    break;
                } catch (Throwable ex) {
                    log.error("Range delay instance failed!", ex);
                }
            }

            log.info("Range delay instance complete！slotId={}", this.currentSlotId);
        }

        @Override
        protected void ignoreTaskList(RedisOperations<String, Object> operations, String zsetKey, List<DelayInstanceAddRequestDTO> list) {
            list.forEach(d -> {
                // Cache keys
                String taskId = d.getTaskId();
                String detailKey = CacheUtil.getDelayDetailTaskIdKey(taskId);
                String listKey = CacheUtil.getAddListKey(DelaySlotUtil.getAddListSlotId(taskId));
                String addressKey = CacheUtil.getDelayDetailWorkerAddressKey(taskId);
                String retryKey = CacheUtil.getDelayRetryTimesKey(taskId);

                // Remove and delete keys
                operations.delete(Arrays.asList(detailKey, addressKey, retryKey));
                operations.opsForZSet().remove(zsetKey, taskId);
                operations.opsForList().remove(listKey, 1, taskId);
            });
        }

        @Override
        protected String getCacheKey(String topic) {
            return CacheUtil.getTopicListKey(topic);
        }
    }
}
