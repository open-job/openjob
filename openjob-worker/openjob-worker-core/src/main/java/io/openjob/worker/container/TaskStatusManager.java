package io.openjob.worker.container;

import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.task.ContainerTaskStatusConsumer;
import io.openjob.worker.task.TaskQueue;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskStatusManager {
    private static final TaskQueue<ContainerTaskStatusRequest> taskQueue;
    private static final ContainerTaskStatusConsumer<ContainerTaskStatusRequest> taskConsumer;

    static {
        taskQueue = new TaskQueue<>(0L, 1024);
        taskConsumer = new ContainerTaskStatusConsumer<>(
                0L,
                1,
                1,
                "Openjob-container-status",
                50,
                "Openjob-container-status-consumer",
                taskQueue
        );

        taskConsumer.start();
    }

    public static void submit(ContainerTaskStatusRequest statusRequest) throws InterruptedException {
        taskQueue.submit(statusRequest);
    }
}
