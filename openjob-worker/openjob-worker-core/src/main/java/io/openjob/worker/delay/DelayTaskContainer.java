package io.openjob.worker.delay;

import io.openjob.worker.context.JobContext;
import scala.Int;

import java.util.concurrent.BlockingQueue;
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

    private final BlockingQueue<Runnable> blockingQueue;

    private final Integer blockingSize = 10;

    public DelayTaskContainer() {
        this.blockingQueue = new LinkedBlockingDeque<>(this.blockingSize);

        AtomicInteger threadId = new AtomicInteger(1);
        executorService = new ThreadPoolExecutor(
                2,
                2,
                30,
                TimeUnit.SECONDS,
                this.blockingQueue,
                r -> new Thread(r, String.format("openjob-delay-container-%s", threadId.getAndIncrement()))
        );
    }

    public Integer pullSize() {
        return this.blockingSize - this.blockingQueue.size();
    }

    public void execute() {
        this.executorService.submit(new DelayThreadTaskProcessor(new JobContext()));
    }
}
