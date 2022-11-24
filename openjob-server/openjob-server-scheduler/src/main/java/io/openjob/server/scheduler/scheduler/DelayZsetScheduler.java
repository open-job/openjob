package io.openjob.server.scheduler.scheduler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.scheduler.contract.DelayScheduler;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Component
public class DelayZsetScheduler implements DelayScheduler {
    private ThreadPoolExecutor executorService;
    private final Map<Long, ZsetRunnable> runnableList = Maps.newConcurrentMap();

    @Override
    public void start() {
        List<Long> slots = this.getCurrentZsetSlots();
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
    }

    @Override
    public void stop() {
        this.executorService.shutdownNow();
        log.info("Range delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        Set<Long> currentSlots = ClusterContext.getCurrentSlots();
        Set<Long> runningSlots = this.runnableList.keySet();

        // Remove slots.
        Set<Long> removeSlots = new HashSet<>(runningSlots);
        removeSlots.removeAll(currentSlots);
        removeSlots.forEach(rs -> Optional.ofNullable(this.runnableList.get(rs)).ifPresent(z -> z.setFinish(true)));

        // Add slots.
        Set<Long> addSlots = new HashSet<>(currentSlots);
        removeSlots.removeAll(runningSlots);
        addSlots.forEach(as -> {
            ZsetRunnable zsetRunnable = Optional.ofNullable(this.runnableList.get(as))
                    .orElseGet(() -> new ZsetRunnable(as));

            // Set finish false.
            zsetRunnable.setFinish(false);
            runnableList.put(as, zsetRunnable);
            executorService.submit(zsetRunnable);
        });

        // Reset executor.
        this.executorService.setMaximumPoolSize(this.runnableList.size());
        this.executorService.setCorePoolSize(this.runnableList.size());
    }

    static class ZsetRunnable implements Runnable {
        private final Long currentSlotId;
        private final AtomicBoolean finish = new AtomicBoolean(false);

        public ZsetRunnable(Long currentSlotId) {
            this.currentSlotId = currentSlotId;
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
         * Set finish.
         *
         * @param finish finish
         */
        public void setFinish(Boolean finish) {
            this.finish.set(finish);
        }

        /**
         * Range delay instance.
         *
         * @param key zset cache key.
         * @throws InterruptedException interruptedException
         */
        private void rangeDelayInstance(String key) throws InterruptedException {
            // Range delay instance from zset.
            Set<Object> rangeObjects = RedisUtil.getTemplate().opsForZSet().rangeByScore(key, DateUtil.timestamp(), -1, 0, 50);

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
            List<String> removeMembers = Lists.newArrayList();
            for (Object rangeObject : rangeObjects) {
                ZSetOperations.TypedTuple<Object> typedTuple = (ZSetOperations.TypedTuple<Object>) rangeObject;
                Object value = typedTuple.getValue();
                removeMembers.add(String.valueOf(value));
            }

            // Delay detail keys.
            List<String> cacheKeys = Lists.newArrayList();
            removeMembers.forEach(rm -> cacheKeys.add(CacheUtil.getDetailKey(rm)));

            // Multi to get detail.
            List<Object> cacheList = RedisUtil.getTemplate().opsForValue().multiGet(cacheKeys);
            if (CollectionUtils.isEmpty(cacheList)) {
                return;
            }

            // Delay detail list.
            List<DelayInstanceAddRequestDTO> detailList = Lists.newArrayList();
            for (Object detail : cacheList) {
                if (Objects.isNull(detail)) {
                    continue;
                }

                if (detail instanceof DelayInstanceAddRequestDTO) {
                    detailList.add((DelayInstanceAddRequestDTO) detail);
                }
            }

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
                        String cacheListKey = CacheUtil.getTopicKey(t);
                        operations.opsForList().rightPushAll(cacheListKey, list.toArray());
                    });

                    // Remove from zset.
                    RedisUtil.getTemplate().opsForZSet().remove(key, removeMembers.toArray());
                    return operations.exec();
                }
            });
        }
    }

    private List<Long> getCurrentZsetSlots() {
        List<Long> slots = Lists.newArrayList();
        ClusterContext.getCurrentSlots().forEach(i -> {
            if (i <= ClusterContext.getSystem().getDelayZsetMaxSlot()) {
                slots.add(i);
            }
        });
        return slots;
    }
}
