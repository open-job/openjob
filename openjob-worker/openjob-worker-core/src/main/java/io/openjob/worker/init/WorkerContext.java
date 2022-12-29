package io.openjob.worker.init;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class WorkerContext {

    /**
     * Online workers.
     */
    private static final Set<String> onlineWorkers = Sets.newConcurrentHashSet();

    /**
     * App id.
     */
    private static final AtomicLong atomicAppId = new AtomicLong(0);

    public void init(Long appId, Set<String> workerList) {
        atomicAppId.set(appId);
        onlineWorkers.addAll(workerList);

        log.info("Worker context initialized! appId={} onlineWorkers={}", appId, workerList);
    }

    public synchronized void refreshOnlineWorkers(Set<String> onlineWorkers) {
        WorkerContext.onlineWorkers.clear();
        WorkerContext.onlineWorkers.addAll(onlineWorkers);
        log.info("Worker context refresh online workers! onlineWorkers={}", onlineWorkers);
    }

    public static Set<String> getOnlineWorkers() {
        return onlineWorkers;
    }

    public static Long getAppId() {
        return atomicAppId.get();
    }
}
