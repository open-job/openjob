package io.openjob.worker.container;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ThreadTaskContainerPool extends TaskContainerPool {
    public static final ThreadTaskContainerPool INSTANCE = new ThreadTaskContainerPool();
    private Map<Long, ExecutorService> threadPoolMap = Maps.newConcurrentMap();

    private ThreadTaskContainerPool() {
        
    }

    @Override
    public void submit(Long jobId, Long jonInstanceId, Long taskId, Integer consumerNum, TaskContainer taskContainer) {
        if (!threadPoolMap.containsKey(jonInstanceId)) {
            synchronized (this) {
                if (!threadPoolMap.containsKey(jonInstanceId)) {
                    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                            consumerNum,
                            consumerNum,
                            30,
                            TimeUnit.SECONDS,
                            new LinkedBlockingDeque<>(),
                            r -> new Thread(r, "Openjob-container-thread")
                    );

                    threadPool.allowCoreThreadTimeOut(true);
                    threadPoolMap.put(jonInstanceId, threadPool);
                }
            }
        }

        threadPoolMap.get(jonInstanceId).execute((Runnable) taskContainer);
    }

    @Override
    public void destroy(Long jonInstanceId) {
        if (threadPoolMap.containsKey(jonInstanceId)) {
            threadPoolMap.get(jonInstanceId).shutdownNow();
            threadPoolMap.remove(jonInstanceId);
        }
    }
}
