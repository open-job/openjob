package io.openjob.server.cluster.task;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.service.WorkerHeartbeatService;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
public class WorkerHeartConsumer extends BaseConsumer<WorkerHeartbeatRequest> {

    private final WorkerHeartbeatService workerHeartbeatService;

    public WorkerHeartConsumer(Long id,
                               Integer consumerCoreThreadNum,
                               Integer consumerMaxThreadNum,
                               String consumerThreadName,
                               Integer pollSize,
                               String pollThreadName,
                               TaskQueue<WorkerHeartbeatRequest> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues);

        // Initialize
        this.setPollIdleTime(2000L);
        this.setPollSleepTime(1000L);
        this.workerHeartbeatService = OpenjobSpringContext.getBean(WorkerHeartbeatService.class);
    }

    @Override
    public void consume(Long id, List<WorkerHeartbeatRequest> tasks) {
        this.consumerExecutor.submit(new WorkerHeartbeatConsumerRunnable(tasks, this.workerHeartbeatService));
    }

    /**
     * Worker heartbeat runnable
     */
    private static class WorkerHeartbeatConsumerRunnable implements Runnable {
        private final List<WorkerHeartbeatRequest> tasks;
        private final WorkerHeartbeatService workerHeartbeatService;

        private WorkerHeartbeatConsumerRunnable(List<WorkerHeartbeatRequest> tasks,
                                                WorkerHeartbeatService workerHeartbeatService) {
            this.tasks = tasks;
            this.workerHeartbeatService = workerHeartbeatService;
        }

        @Override
        public void run() {
            this.workerHeartbeatService.batchHeartbeat(this.tasks);
        }
    }
}
