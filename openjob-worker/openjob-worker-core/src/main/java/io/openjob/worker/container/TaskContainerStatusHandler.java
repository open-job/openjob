package io.openjob.worker.container;

import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.task.ContainerTaskStatusConsumer;
import io.openjob.worker.task.TaskQueue;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskContainerStatusHandler {
    private static final TaskQueue<ContainerTaskStatusRequest> QUEUES = new TaskQueue<>(0L, 1024);
    private static ContainerTaskStatusConsumer<ContainerTaskStatusRequest> HANDLER;

    static {
        HANDLER = new ContainerTaskStatusConsumer<ContainerTaskStatusRequest>(
                0L,
                1,
                10,
                "",
                1,
                "",
                QUEUES
        );
    }

    public static void report(ContainerTaskStatusRequest statusReq) {
        try {
            QUEUES.submit(statusReq);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        HANDLER.stop();
    }
}
