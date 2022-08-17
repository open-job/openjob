package io.openjob.worker.container;

import com.google.common.collect.Maps;
import io.openjob.worker.context.JobContext;

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
    private ThreadLocal<JobContext> contextLocal = new ThreadLocal<>();


    private ThreadTaskContainerPool() {

    }

    @Override
    public void submit(Long jobId, Long jobInstanceId, Long taskId, Integer consumerNum, TaskContainer taskContainer) {
        if (!threadPoolMap.containsKey(jobInstanceId)) {
            synchronized (this) {
                if (!threadPoolMap.containsKey(jobInstanceId)) {
                    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                            consumerNum,
                            consumerNum,
                            30,
                            TimeUnit.SECONDS,
                            new LinkedBlockingDeque<>(),
                            r -> new Thread(r, "Openjob-container-thread")
                    );

                    threadPool.allowCoreThreadTimeOut(true);
                    threadPoolMap.put(jobInstanceId, threadPool);
                }
            }
        }

        threadPoolMap.get(jobInstanceId).execute((Runnable) taskContainer);
    }

    @Override
    public void destroy(Long jonInstanceId) {
        if (threadPoolMap.containsKey(jonInstanceId)) {
            threadPoolMap.get(jonInstanceId).shutdownNow();
            threadPoolMap.remove(jonInstanceId);
        }
    }

    @Override
    public void setJobContext(JobContext jobContext) {
        this.contextLocal.set(jobContext);
    }

    @Override
    public void removeJobContext() {
        this.contextLocal.remove();
    }

    @Override
    public JobContext getJobContext() {
        return this.contextLocal.get();
    }
}
