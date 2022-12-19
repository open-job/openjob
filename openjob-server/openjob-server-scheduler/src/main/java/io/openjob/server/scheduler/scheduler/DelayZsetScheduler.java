package io.openjob.server.scheduler.scheduler;

import io.openjob.common.SpringContext;
import io.openjob.common.util.DateUtil;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Component
public class DelayZsetScheduler extends AbstractDelayScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentZsetSlots();
        int maxSize = slots.size() > 0 ? slots.size() : 1;

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
        this.executorService.shutdownNow();
        log.info("Range delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentZsetSlots();
        this.refreshSlots(slots, ZsetRunnable::new);

        log.info("Refresh range delay instance slots{}", slots);
    }

    static class ZsetRunnable extends AbstractRunnable {
        private final DelayData delayData;
        public ZsetRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.delayData = SpringContext.getBean(DelayData.class);
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

        /**
         * Range delay instance.
         *
         * @param key zset cache key.
         * @throws InterruptedException interruptedException
         */
        private void rangeDelayInstance(String key) throws InterruptedException {
            // Range delay instance from zset.
            Set<Object> rangeObjects = RedisUtil.getTemplate().opsForZSet().rangeByScore(key, 0, DateUtil.timestamp(), 0, 50);

            // Delay instance is empty.
            if (CollectionUtils.isEmpty(rangeObjects)) {
                Thread.sleep(500);
                return;
            }

            // Push to list and remove from zset
            this.pushAndRemoveDelayInstance(key, rangeObjects);
        }

        /**
         * Push to list and remove from zset
         *
         * @param key          zset cache key.
         * @param rangeObjects range objects.
         */
        @SuppressWarnings("unchecked")
        private void pushAndRemoveDelayInstance(String key, Set<Object> rangeObjects) {
            // Remove zset members.
            List<String> removeMembers = rangeObjects.stream().map(String::valueOf)
                    .collect(Collectors.toList());

            // Get delay instance detail list
            List<DelayInstanceAddRequestDTO> detailList = this.delayData.getDelayInstanceList(removeMembers);

            // Group by topic.
            Map<String, List<DelayInstanceAddRequestDTO>> detailListMap = detailList.stream()
                    .collect(Collectors.groupingBy(DelayInstanceAddRequestDTO::getTopic));

            // Execute by pipelined.
            RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                    operations.multi();

                    // Push by topic
                    detailListMap.forEach((t, list) -> {
                        String cacheListKey = CacheUtil.getTopicListKey(t);
                        operations.opsForList().rightPushAll(cacheListKey, list.stream().map(DelayInstanceAddRequestDTO::getTaskId).toArray());
                    });

                    // Remove from zset.
                    RedisUtil.getTemplate().opsForZSet().remove(key, removeMembers.toArray());
                    operations.exec();
                    return null;
                }
            });
        }
    }
}
