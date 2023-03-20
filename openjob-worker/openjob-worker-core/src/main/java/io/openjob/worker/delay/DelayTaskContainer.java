package io.openjob.worker.delay;

import io.openjob.common.util.DateUtil;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.dto.DelayInstanceDTO;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskContainer {
    private final Long id;
    private final ThreadPoolExecutor executorService;

    /**
     * New delay task container.
     *
     * @param id           id
     * @param blockingSize blocking size.
     * @param concurrency  concurrency
     */
    public DelayTaskContainer(Long id, Integer blockingSize, Integer concurrency) {
        this.id = id;

        // Task container thread pool
        LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>(blockingSize);
        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                1,
                concurrency,
                30,
                TimeUnit.SECONDS,
                blockingDeque,
                r -> new Thread(r, String.format("openjob-delay-container-%s", threadId.getAndIncrement()))
        );
        executorService.allowCoreThreadTimeOut(true);
    }

    /**
     * Execute
     *
     * @param instanceList instance list.
     */
    public void execute(List<DelayInstanceDTO> instanceList) {
        DelayDAO.INSTANCE.updatePullSizeById(this.id, -instanceList.size());

        Long timestamp = DateUtil.timestamp();
        instanceList.forEach(i -> {
            JobContext jobContext = new JobContext();
            jobContext.setDelayId(i.getDelayId());
            jobContext.setDelayParams(i.getDelayParams());
            jobContext.setDelayExtra(i.getDelayExtra());
            jobContext.setProcessorInfo(i.getProcessorInfo());
            jobContext.setFailRetryInterval(i.getFailRetryInterval());
            jobContext.setFailRetryTimes(i.getFailRetryTimes());
            jobContext.setDelayTaskId(i.getTaskId());
            jobContext.setDelayTopic(i.getTopic());
            Future<?> future = this.executorService.submit(new DelayThreadTaskProcessor(jobContext));
            DelayTaskManager.INSTANCE.addTask(i.getTaskId(), future, timestamp + i.getExecuteTimeout());
        });
    }

    /**
     * Update concurrency
     *
     * @param concurrency concurrency
     */
    public void updateConcurrency(Integer concurrency) {
        this.executorService.setMaximumPoolSize(concurrency);
    }

    /**
     * Stop
     */
    public void stop() {
        this.executorService.shutdownNow();
    }
}
