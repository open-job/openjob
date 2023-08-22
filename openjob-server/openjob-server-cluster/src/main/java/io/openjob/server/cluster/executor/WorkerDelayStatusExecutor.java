package io.openjob.server.cluster.executor;

import io.openjob.common.request.WorkerDelayStatusRequest;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.task.WorkerDelayStatusConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class WorkerDelayStatusExecutor {
    private final TaskQueue<WorkerDelayStatusRequest> queue;

    /**
     * New
     */
    public WorkerDelayStatusExecutor() {
        this.queue = new TaskQueue<>(0L, 1024);

        //Consumer
        WorkerDelayStatusConsumer consumer = new WorkerDelayStatusConsumer(
                0L,
                1,
                16,
                "Openjob-heartbeat-executor",
                50,
                "Openjob-heartbeat-consumer",
                this.queue
        );
        consumer.start();
    }

    /**
     * Submit request
     *
     * @param request request
     */
    public void submit(WorkerDelayStatusRequest request) {
        try {
            this.queue.submit(request);
        } catch (InterruptedException e) {
            log.error("Worker heartbeat submit failed!", e);
        }
    }
}
