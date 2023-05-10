package io.openjob.worker.delay;

import io.openjob.common.request.WorkerDelayTaskRequest;
import io.openjob.worker.task.DelayStatusConsumer;
import io.openjob.worker.task.TaskQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class DelayStatusReporter {
    private static final TaskQueue<WorkerDelayTaskRequest> TASK_QUEUE;
    private static final DelayStatusConsumer TASK_CONSUMER;

    static {
        TASK_QUEUE = new TaskQueue<>(0L, 1024);
        TASK_CONSUMER = new DelayStatusConsumer(
                0L,
                1,
                1,
                "Openjob-delay-status",
                50,
                "Openjob-delay-status-consumer",
                TASK_QUEUE
        );

        TASK_CONSUMER.start();
    }

    /**
     * Report delay status.
     *
     * @param workerDelayStatusRequest workerDelayStatusRequest
     */
    public static void report(WorkerDelayTaskRequest workerDelayStatusRequest) {
        try {
            TASK_QUEUE.submit(workerDelayStatusRequest);
        } catch (InterruptedException e) {
            log.error("Delay status report failed!", e);
        }
    }
}
