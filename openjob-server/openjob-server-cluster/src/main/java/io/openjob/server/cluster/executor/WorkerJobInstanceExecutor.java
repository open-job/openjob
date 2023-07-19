package io.openjob.server.cluster.executor;

import io.openjob.common.request.WorkerJobInstanceStatusRequest;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.task.WorkerJobInstanceConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class WorkerJobInstanceExecutor {
    private final TaskQueue<WorkerJobInstanceStatusRequest> queue;

    /**
     * New
     */
    public WorkerJobInstanceExecutor() {
        this.queue = new TaskQueue<>(0L, 1024);

        //Consumer
        WorkerJobInstanceConsumer consumer = new WorkerJobInstanceConsumer(
                0L,
                1,
                32,
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
    public void submit(WorkerJobInstanceStatusRequest request) {
        try {
            this.queue.submit(request);
        } catch (InterruptedException e) {
            log.error("Worker heartbeat submit failed!", e);
        }
    }
}
