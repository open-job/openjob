package io.openjob.server.scheduler.scheduler;

import io.openjob.common.util.DateUtil;
import io.openjob.common.util.DelayUtil;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayFailZsetScheduler extends AbstractDelayZsetScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getFailCurrentZsetSlots();
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
                r -> new Thread(r, String.format("delay-fail-zset-%s", threadId.getAndIncrement()))
        );

        slots.forEach(s -> {
            FailZsetRunnable zsetRunnable = new FailZsetRunnable(s);
            runnableList.put(s, zsetRunnable);
            executorService.submit(zsetRunnable);
        });

        executorService.allowCoreThreadTimeOut(true);
        log.info("Range Fail Delay instance slots{}", slots);
    }

    @Override
    public void stop() {
        // Not slots on current node.
        if (Objects.isNull(executorService)) {
            return;
        }

        this.executorService.shutdown();
        log.info("Range fail delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getFailCurrentZsetSlots();
        this.refreshSlots(slots, FailZsetRunnable::new);

        log.info("Refresh fail range delay instance slots{}", slots);
    }

    static class FailZsetRunnable extends AbstractZsetRunnable {

        /**
         * New ZsetRunnable.
         *
         * @param currentSlotId current slot id.
         */
        public FailZsetRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.isFailZset = true;
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getFailZsetKey(this.currentSlotId);
            log.info("Range delay fail instance begin！slotId={}", this.currentSlotId);

            while (!finish.get()) {
                try {
                    this.rangeDelayInstance(key);
                } catch (InterruptedException interruptedException) {
                    log.info("Range delay fail instance interrupted complete！slotId={}", this.currentSlotId);
                } catch (BeanCreationNotAllowedException beanCreationNotAllowedException) {
                    // Fixed executor shutdown error.
                    log.info("Range delay fail instance destroy complete！slotId={}", this.currentSlotId);
                    break;
                } catch (Throwable ex) {
                    log.error("Range delay fail instance failed!", ex);
                    this.failSleep();
                }
            }

            log.info("Range delay fail instance complete！slotId={}", this.currentSlotId);
        }

        @Override
        protected String getCacheKey(String topic) {
            return CacheUtil.getTopicListKey(DelayUtil.getFailDelayTopic(topic));
        }
    }
}
