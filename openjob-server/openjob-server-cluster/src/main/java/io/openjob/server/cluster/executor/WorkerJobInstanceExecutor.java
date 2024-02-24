package io.openjob.server.cluster.executor;

import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.dto.WorkerJobInstanceStatusReqDTO;
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
    private final TaskQueue<WorkerJobInstanceStatusReqDTO> queue;

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
                "Openjob-instance-executor",
                20,
                "Openjob-instance-consumer",
                this.queue
        );
        consumer.start();
    }

    /**
     * Submit request
     *
     * @param request request
     */
    public void submit(WorkerJobInstanceStatusReqDTO request) {
        try {
            this.queue.submit(request);
        } catch (InterruptedException e) {
            log.error("Worker heartbeat submit failed!", e);
        }
    }
}
