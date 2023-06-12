package io.openjob.server.cluster.executor;

import io.openjob.common.request.WorkerJobInstanceTaskLogRequest;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.task.WorkerTaskLogConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@Slf4j
@Component
public class WorkerTaskLogExecutor {
    private final TaskQueue<WorkerJobInstanceTaskLogRequest> queue;

    /**
     * New
     */
    public WorkerTaskLogExecutor() {
        this.queue = new TaskQueue<>(0L, 64);

        // Consumer
        WorkerTaskLogConsumer consumer = new WorkerTaskLogConsumer(
                0L,
                1,
                16,
                "Openjob-log-executor",
                50,
                "Openjob-log-consumer",
                this.queue
        );
        consumer.start();
    }

    /**
     * Submit request
     *
     * @param request request
     */
    public void submit(WorkerJobInstanceTaskLogRequest request) {
        try {
            this.queue.submit(request);
        } catch (InterruptedException e) {
            log.error("Worker task log submit failed!", e);
        }
    }
}
