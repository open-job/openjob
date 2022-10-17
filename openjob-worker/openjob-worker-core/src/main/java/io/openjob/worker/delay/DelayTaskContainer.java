package io.openjob.worker.delay;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.dto.DelayInstanceDTO;
import org.graalvm.compiler.core.common.alloc.Trace;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskContainer {

    private final ThreadPoolExecutor executorService;

    private final Long id;
    private LinkedBlockingDeque<Runnable> blockingDeque;

    public DelayTaskContainer(Long id, Integer blockingSize, Integer concurrency) {
        this.id = id;
        this.blockingDeque = new LinkedBlockingDeque<>(blockingSize);

        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                1,
                concurrency,
                30,
                TimeUnit.SECONDS,
                this.blockingDeque,
                r -> new Thread(r, String.format("openjob-delay-container-%s", threadId.getAndIncrement()))
        );

        executorService.allowCoreThreadTimeOut(true);
    }

    public void execute(List<DelayInstanceDTO> instanceList) {
        DelayDAO.INSTANCE.updatePullSizeById(this.id, -instanceList.size());

        instanceList.forEach(i -> {
            JobContext jobContext = new JobContext();
            jobContext.setDelayId(i.getDelayId());
            jobContext.setDelayParams(i.getDelayParams());
            jobContext.setDelayExtra(i.getDelayExtra());
            this.executorService.submit(new DelayThreadTaskProcessor(jobContext));
        });
    }

    public void updateConcurrency(Integer concurrency) {
        this.executorService.setMaximumPoolSize(concurrency);
    }

    public void stop() {
        this.executorService.shutdownNow();
    }
}
