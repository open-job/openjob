package io.openjob.server.cluster.task;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.dto.WorkerHeartbeatReqDTO;
import io.openjob.server.cluster.manager.WorkerHeartbeatManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@Slf4j
public class WorkerHeartConsumer extends BaseConsumer<WorkerHeartbeatReqDTO> {

    public WorkerHeartConsumer(Long id,
                               Integer consumerCoreThreadNum,
                               Integer consumerMaxThreadNum,
                               String consumerThreadName,
                               Integer pollSize,
                               String pollThreadName,
                               TaskQueue<WorkerHeartbeatReqDTO> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues, 2000L, 1000L);
    }

    @Override
    public void consume(Long id, List<WorkerHeartbeatReqDTO> tasks) {
        this.consumerExecutor.submit(new WorkerHeartbeatConsumerRunnable(tasks));
    }

    /**
     * Worker heartbeat runnable
     */
    private static class WorkerHeartbeatConsumerRunnable implements Runnable {
        private final List<WorkerHeartbeatReqDTO> tasks;

        private WorkerHeartbeatConsumerRunnable(List<WorkerHeartbeatReqDTO> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            try {
                OpenjobSpringContext.getBean(WorkerHeartbeatManager.class).batchHeartbeat(this.tasks);
            } catch (Throwable throwable) {
                log.error("Worker heartbeat failed!", throwable);
            }
        }
    }
}
