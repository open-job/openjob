package io.openjob.worker.container;

import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.task.ContainerTaskStatusConsumer;
import io.openjob.worker.task.TaskQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class TaskStatusReporter {
    private static final TaskQueue<ContainerTaskStatusRequest> TASK_QUEUE;
    private static final ContainerTaskStatusConsumer<ContainerTaskStatusRequest> TASK_CONSUMER;

    static {
        TASK_QUEUE = new TaskQueue<>(0L, 1024);
        TASK_CONSUMER = new ContainerTaskStatusConsumer<>(
                0L,
                1,
                1,
                "Openjob-container-status",
                50,
                "Openjob-container-status-consumer",
                TASK_QUEUE
        );

        TASK_CONSUMER.start();
    }

    /**
     * Report status request.
     *
     * @param statusRequest status request.
     */
    public static void report(ContainerTaskStatusRequest statusRequest) {
        try {
            TASK_QUEUE.submit(statusRequest);
        } catch (InterruptedException e) {
            log.error("Task Status report failed!", e);
        }
    }
}
