package io.openjob.worker.master;

import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.task.DistributeStatusConsumer;
import io.openjob.worker.task.TaskQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class DistributeStatusHandler {
    private static final TaskQueue<ContainerTaskStatusRequest> TASK_QUEUE;
    private static final DistributeStatusConsumer TASK_CONSUMER;

    static {
        TASK_QUEUE = new TaskQueue<>(0L, 10240);
        TASK_CONSUMER = new DistributeStatusConsumer(
                0L,
                2,
                8,
                "Openjob-distribute-status",
                50,
                "Openjob-distribute-status-consumer",
                TASK_QUEUE
        );

        TASK_CONSUMER.start();
    }

    /**
     * Handle
     *
     * @param statusRequestList statusRequestList
     */
    public static void handle(List<ContainerTaskStatusRequest> statusRequestList) {
        statusRequestList.forEach(s -> {
            try {
                TASK_QUEUE.submit(s);
            } catch (InterruptedException e) {
                log.error("Distribute status handle failed!", e);
            }
        });
    }
}
