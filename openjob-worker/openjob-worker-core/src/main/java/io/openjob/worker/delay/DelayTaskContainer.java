package io.openjob.worker.delay;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.DelayDAO;
import io.openjob.worker.dto.DelayInstanceDTO;

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

    private final ExecutorService executorService;

    private final Long id;

    private final String topic;

    public DelayTaskContainer(Long id, String topic, Integer concurrency) {
        this.id = id;
        this.topic = topic;

        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                concurrency,
                concurrency,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                r -> new Thread(r, String.format("openjob-delay-container-%s", threadId.getAndIncrement()))
        );
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

    public void stop() {
        this.executorService.shutdownNow();
    }
}
