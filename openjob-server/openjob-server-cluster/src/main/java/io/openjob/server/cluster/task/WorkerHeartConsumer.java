package io.openjob.server.cluster.task;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.service.WorkerHeartbeatService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@Slf4j
public class WorkerHeartConsumer extends BaseConsumer<WorkerHeartbeatRequest> {

    public WorkerHeartConsumer(Long id,
                               Integer consumerCoreThreadNum,
                               Integer consumerMaxThreadNum,
                               String consumerThreadName,
                               Integer pollSize,
                               String pollThreadName,
                               TaskQueue<WorkerHeartbeatRequest> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues, 2000L, 1000L);
    }

    @Override
    public void consume(Long id, List<WorkerHeartbeatRequest> tasks) {
        this.consumerExecutor.submit(new WorkerHeartbeatConsumerRunnable(tasks));
    }

    /**
     * Worker heartbeat runnable
     */
    private static class WorkerHeartbeatConsumerRunnable implements Runnable {
        private final List<WorkerHeartbeatRequest> tasks;

        private WorkerHeartbeatConsumerRunnable(List<WorkerHeartbeatRequest> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            try {
                OpenjobSpringContext.getBean(WorkerHeartbeatService.class).batchHeartbeat(this.tasks);
            } catch (Throwable throwable) {
                log.error("Worker heartbeat failed!", throwable);
            }
        }
    }
}
