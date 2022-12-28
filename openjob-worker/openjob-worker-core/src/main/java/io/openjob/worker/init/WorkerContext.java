package io.openjob.worker.init;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
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
    }

    public synchronized Set<String> refreshOnlineWorkers(Set<String> latestWorkers) {
        Set<String> currentWorkers = new HashSet<>(onlineWorkers);
        currentWorkers.removeAll(latestWorkers);
        onlineWorkers.clear();
        onlineWorkers.addAll(latestWorkers);
        return currentWorkers;
    }

    public static Set<String> getOnlineWorkers() {
        return onlineWorkers;
    }

    public static Long getAppId() {
        return atomicAppId.get();
    }
}
