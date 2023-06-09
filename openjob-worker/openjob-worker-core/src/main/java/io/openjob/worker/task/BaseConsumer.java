package io.openjob.worker.task;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public abstract class BaseConsumer<T> {

    /**
     * Poll ide time(ms).
     */
    protected Long pollIdeTime = 1000L;

    /**
     * Poll sleep time(ms).
     */
    protected Long pollSleepTime = 500L;

    protected final Long id;
    protected final Integer consumerCoreThreadNum;
    protected final Integer consumerMaxThreadNum;
    protected final String consumerThreadName;
    protected ThreadPoolExecutor consumerExecutor;
    protected Integer pollSize;
    protected String pollThreadName;
    protected Thread pollThread;
    protected TaskQueue<T> queues;
    protected ThreadPoolExecutor pullExecutor;
    protected AtomicInteger activePollNum = new AtomicInteger(0);

    /**
     * New BaseConsumer
     *
     * @param id                    id
     * @param consumerCoreThreadNum consumerCoreThreadNum
     * @param consumerMaxThreadNum  consumerMaxThreadNum
     * @param consumerThreadName    consumerThreadName
     * @param pollSize              pollSize
     * @param pollThreadName        pollThreadName
     * @param queues                queues
     */
    public BaseConsumer(Long id,
                        Integer consumerCoreThreadNum,
                        Integer consumerMaxThreadNum,
                        String consumerThreadName,
                        Integer pollSize,
                        String pollThreadName,
                        TaskQueue<T> queues) {
        this.id = id;
        this.consumerCoreThreadNum = consumerCoreThreadNum;
        this.consumerMaxThreadNum = consumerMaxThreadNum;
        this.consumerThreadName = consumerThreadName;
        this.pollSize = pollSize;
        this.pollThreadName = pollThreadName;
        this.queues = queues;
    }

    /**
     * Start
     */
    @SuppressWarnings("all")
    public void start() {
        // Handler thread executor.
        consumerExecutor = new ThreadPoolExecutor(
                this.consumerCoreThreadNum,
                this.consumerMaxThreadNum,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10240),
                new ThreadFactory() {
                    private final AtomicInteger index = new AtomicInteger(1);

                    @Override
                    public Thread newThread(@Nonnull Runnable r) {
                        return new Thread(r, String.format("%s-%d-%d", consumerThreadName, id, index.getAndIncrement()));
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        this.pullExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(1), r -> new Thread(r, "pull"));

        this.pullExecutor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    List<T> tasks = this.pollTasks();
                    if (tasks.size() < this.pollSize) {
                        if (tasks.isEmpty()) {
                            Thread.sleep(1000L);
                            continue;
                        }
                        Thread.sleep(500L);
                    }
                }
            } catch (Throwable ex) {
                log.warn("Task consumer failed! message={}", ex.getMessage());
            }
        });
    }

    /**
     * Consume.
     *
     * @param id    id
     * @param tasks tasks
     */
    public abstract void consume(Long id, List<T> tasks);

    /**
     * Stop
     */
    public void stop() {
        // Poll thread
        if (Objects.nonNull(pollThread)) {
            pollThread.interrupt();
        }

        // Handle thread pool
        if (Objects.nonNull(consumerExecutor)) {
            consumerExecutor.shutdownNow();
        }

        if (Objects.nonNull(this.queues)) {
            this.queues.clear();
        }

        if (Objects.nonNull(this.pullExecutor)) {
            this.pullExecutor.shutdownNow();
        }
    }

    private synchronized List<T> pollTasks() {
        List<T> tasks = queues.poll(this.pollSize);
        if (!tasks.isEmpty()) {
            this.activePollNum.incrementAndGet();
            this.consume(id, tasks);
        }
        return tasks;
    }

    /**
     * Whether is active.
     *
     * @return boolean
     */
    public synchronized boolean isActive() {
        return queues.size() > 0 || activePollNum.get() > 0;
    }
}
