package io.openjob.worker.task;

import io.openjob.common.request.WorkerDelayStatusRequest;
import io.openjob.common.request.WorkerDelayTaskRequest;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.init.WorkerConfig;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class DelayStatusConsumer extends BaseConsumer<WorkerDelayTaskRequest> {

    public DelayStatusConsumer(Long id,
                               Integer consumerCoreThreadNum,
                               Integer consumerMaxThreadNum,
                               String consumerThreadName,
                               Integer pollSize,
                               String pollThreadName,
                               TaskQueue<WorkerDelayTaskRequest> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues);
    }

    @Override
    public void consume(Long id, List<WorkerDelayTaskRequest> tasks) {
        consumerExecutor.submit(new DelayStatusConsumerRunnable(tasks));
    }

    private static class DelayStatusConsumerRunnable implements Runnable {
        private final List<WorkerDelayTaskRequest> tasks;

        private DelayStatusConsumerRunnable(List<WorkerDelayTaskRequest> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            WorkerDelayStatusRequest workerDelayStatusRequest = new WorkerDelayStatusRequest();
            workerDelayStatusRequest.setTaskList(tasks);
            WorkerActorSystem.atLeastOnceDelivery(workerDelayStatusRequest, null);
        }
    }
}
