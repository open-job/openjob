package io.openjob.server.scheduler.scheduler;

import io.openjob.common.SpringContext;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.dto.DelayInstanceStatusRequestDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.scheduler.util.DelaySlotUtil;
import io.openjob.server.scheduler.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayStatusListScheduler extends AbstractDelayScheduler {
    @Override
    public void start() {
        List<Long> slots = DelaySlotUtil.getCurrentStatusListSlots();
        int maxSize = slots.size() > 0 ? slots.size() : 1;

        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                maxSize,
                maxSize,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(maxSize),
                r -> new Thread(r, String.format("delay-status-list-%s", threadId.getAndIncrement()))
        );

        slots.forEach(s -> {
            DelayStatusListScheduler.StatusListRunnable listRunnable = new DelayStatusListScheduler.StatusListRunnable(s);
            runnableList.put(s, listRunnable);
            executorService.submit(listRunnable);
        });

        executorService.allowCoreThreadTimeOut(true);
        log.info("Status List Delay instance slots{}", slots);
    }

    @Override
    public void stop() {
        this.executorService.shutdown();
        log.info("Status List delay instance shutdown now!");
    }

    @Override
    public void refresh() {
        List<Long> slots = DelaySlotUtil.getCurrentStatusListSlots();
        this.refreshSlots(slots, DelayAddListScheduler.AddListRunnable::new);

        log.info("Refresh status list delay instance slots{}", slots);
    }

    static class StatusListRunnable extends AbstractRunnable {
        protected final DelayInstanceDAO delayInstanceDAO;

        public StatusListRunnable(Long currentSlotId) {
            super(currentSlotId);
            this.delayInstanceDAO = SpringContext.getBean(DelayInstanceDAO.class);
        }

        @Override
        public void run() {
            // Cache key.
            String key = CacheUtil.getStatusListKey(this.currentSlotId);
            log.info("Status list delay instance begin！slotId={}", this.currentSlotId);

            while (!finish.get()) {
                try {
                    this.batchUpdateStatus(key);
                } catch (InterruptedException interruptedException) {
                    log.info("Status list delay instance interrupted complete！slotId={}", this.currentSlotId);
                } catch (BeanCreationNotAllowedException beanCreationNotAllowedException) {
                    // Fixed executor shutdown error.
                    log.info("Status list delay instance destroy complete！slotId={}", this.currentSlotId);
                    break;
                } catch (Throwable ex) {
                    log.error("Status list delay instance failed!", ex);
                }
            }

            log.info("Status list delay instance complete！slotId={}", this.currentSlotId);
        }

        public void batchUpdateStatus(String key) throws InterruptedException {
            // Cache list.
            List<Object> popObjects = RedisUtil.popAndRemoveFromList(key, 100);
            if (CollectionUtils.isEmpty(popObjects)) {
                Thread.sleep(500);
                return;
            }

            Map<String, DelayInstance> listMap = new HashMap<>();
            popObjects.forEach(o -> {
                DelayInstanceStatusRequestDTO delayStatus = (DelayInstanceStatusRequestDTO) o;
                DelayInstance mapInstance = listMap.get(delayStatus.getTaskId());
                DelayInstance delayInstance = new DelayInstance();
                delayInstance.setTaskId(delayStatus.getTaskId());
                delayInstance.setStatus(delayStatus.getStatus());
                delayInstance.setWorkerAddress(delayStatus.getWorkerAddress());
                delayInstance.setCompleteTime(delayStatus.getCompleteTime());

                // Fixed many status for one task id.
                // Select max status.
                if (Objects.isNull(mapInstance) || mapInstance.getStatus() < delayStatus.getStatus()) {
                    listMap.put(delayStatus.getTaskId(), delayInstance);
                }
            });

            // Batch update.
            this.delayInstanceDAO.batchUpdateStatus(new ArrayList<>(listMap.values()));
        }
    }
}
