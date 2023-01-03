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
    private static final Set<String> ONLINE_WORKERS = Sets.newConcurrentHashSet();

    /**
     * App id.
     */
    private static final AtomicLong ATOMIC_APPID = new AtomicLong(0);

    /**
     * Init
     *
     * @param appId      appId
     * @param workerList workerList
     */
    public void init(Long appId, Set<String> workerList) {
        ATOMIC_APPID.set(appId);
        ONLINE_WORKERS.addAll(workerList);

        log.info("Worker context initialized! appId={} onlineWorkers={}", appId, workerList);
    }

    /**
     * Refresh online workers.
     *
     * @param onlineWorkers online workers.
     */
    public synchronized void refreshOnlineWorkers(Set<String> onlineWorkers) {
        WorkerContext.ONLINE_WORKERS.clear();
        WorkerContext.ONLINE_WORKERS.addAll(onlineWorkers);
        log.info("Worker context refresh online workers! onlineWorkers={}", onlineWorkers);
    }

    public static Set<String> getOnlineWorkers() {
        return ONLINE_WORKERS;
    }

    public static Long getAppId() {
        return ATOMIC_APPID.get();
    }
}
