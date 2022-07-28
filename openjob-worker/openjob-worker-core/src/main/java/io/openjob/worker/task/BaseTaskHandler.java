package io.openjob.worker.task;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class BaseTaskHandler<T> {
    private final Long id;
    private final Integer handlerCoreThreadNum;
    private final Integer handlerMaxThreadNum;
    private final String handlerThreadName;
    private ThreadPoolExecutor handlerExecutor;
    private Integer pollSize;
    private String pollThreadName;
    private Thread pollThread;
    private TaskQueue<T> queues;
    private AtomicInteger activePollNum = new AtomicInteger(0);

    public BaseTaskHandler(Long id,
                           Integer handlerCoreThreadNum,
                           Integer handlerMaxThreadNum,
                           String handlerThreadName,
                           Integer pollSize,
                           String pollThreadName,
                           TaskQueue<T> queues) {
        this.id = id;
        this.handlerCoreThreadNum = handlerCoreThreadNum;
        this.handlerMaxThreadNum = handlerMaxThreadNum;
        this.handlerThreadName = handlerThreadName;
        this.pollSize = pollSize;
        this.pollThreadName = pollThreadName;
        this.queues = queues;
    }

    public void start() {
        // Handler thread executor.
        handlerExecutor = new ThreadPoolExecutor(
                this.handlerCoreThreadNum,
                this.handlerMaxThreadNum,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10240),
                new ThreadFactory() {
                    private final AtomicInteger index = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, String.format("%s-%d-%d", handlerThreadName, id, index.getAndIncrement()));
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        pollThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                List<T> tasks = this.pollTasks();
                if (tasks.size() < this.pollSize) {
                    
                }
            }
        });
        pollThread.start();
    }

    public abstract void handle(Long id, List<T> tasks);

    private synchronized List<T> pollTasks() {
        List<T> tasks = queues.poll(this.pollSize);
        if (!tasks.isEmpty()) {
            this.activePollNum.incrementAndGet();
            this.handle(id, tasks);
        }
        return tasks;
    }
}
