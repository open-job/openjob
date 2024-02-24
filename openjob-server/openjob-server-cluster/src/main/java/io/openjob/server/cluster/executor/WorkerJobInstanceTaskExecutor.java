package io.openjob.server.cluster.executor;

import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskBatchReqDTO;
import io.openjob.server.cluster.task.WorkerJobInstanceTaskConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class WorkerJobInstanceTaskExecutor {
    private final TaskQueue<WorkerJobInstanceTaskBatchReqDTO> queue;

    /**
     * New
     */
    public WorkerJobInstanceTaskExecutor() {
        this.queue = new TaskQueue<>(0L, 1024);

        //Consumer
        WorkerJobInstanceTaskConsumer consumer = new WorkerJobInstanceTaskConsumer(
                0L,
                1,
                32,
                "Openjob-instance-task-executor",
                4,
                "Openjob-instance-task-consumer",
                this.queue
        );
        consumer.start();
    }

    /**
     * Submit request
     *
     * @param request request
     */
    public void submit(WorkerJobInstanceTaskBatchReqDTO request) {
        try {
            this.queue.submit(request);
        } catch (InterruptedException e) {
            log.error("Worker heartbeat submit failed!", e);
        }
    }
}
