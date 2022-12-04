package io.openjob.server.scheduler.scheduler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.openjob.server.scheduler.contract.DelayScheduler;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.util.RedisUtil;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class AbstractDelayScheduler implements DelayScheduler {
    protected ThreadPoolExecutor executorService;
    protected final Map<Long, AbstractRunnable> runnableList = Maps.newConcurrentMap();

    /**
     * Refresh slots.
     *
     * @param slots    slots
     * @param function function
     */
    protected void refreshSlots(List<Long> slots, Function<Long, AbstractRunnable> function) {
        Set<Long> currentSlots = new HashSet<>(slots);
        Set<Long> runningSlots = this.runnableList.keySet();

        // Remove slots.
        Set<Long> removeSlots = new HashSet<>(runningSlots);
        removeSlots.removeAll(currentSlots);
        removeSlots.forEach(rs -> Optional.ofNullable(this.runnableList.get(rs)).ifPresent(l -> l.setFinish(true)));

        // Add slots.
        Set<Long> addSlots = new HashSet<>(currentSlots);
        addSlots.removeAll(runningSlots);
        addSlots.forEach(as -> {
            AbstractRunnable listRunnable = Optional.ofNullable(this.runnableList.get(as))
                    .orElseGet(() -> function.apply(as));

            // Set finish false.
            listRunnable.setFinish(false);
            runnableList.put(as, listRunnable);
            executorService.submit(listRunnable);
        });

        // Reset executor.
        // Must first CorePoolSize and second MaximumPoolSize
        this.executorService.setCorePoolSize(currentSlots.size());
        this.executorService.setMaximumPoolSize(currentSlots.size());
    }

    /**
     * Abstract runnable.
     */
    abstract static class AbstractRunnable implements Runnable {
        protected final Long currentSlotId;
        protected final AtomicBoolean finish = new AtomicBoolean(false);

        /**
         * Abstract runnable.
         *
         * @param currentSlotId current slot id.
         */
        public AbstractRunnable(Long currentSlotId) {
            this.currentSlotId = currentSlotId;
        }

        /**
         * Get delay instance list.
         *
         * @param cacheKeys cache keys.
         * @return List
         */
        protected List<DelayInstanceAddRequestDTO> getDelayInstanceList(List<String> cacheKeys) {
            // Multi to get detail.
            List<Object> cacheList = RedisUtil.getTemplate().opsForValue().multiGet(cacheKeys);
            if (CollectionUtils.isEmpty(cacheList)) {
                return Collections.emptyList();
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
            return detailList;
        }

        /**
         * Set finish.
         *
         * @param finish finish
         */
        public void setFinish(Boolean finish) {
            this.finish.set(finish);
        }
    }
}
