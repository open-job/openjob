package io.openjob.server.cluster.task;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.request.WorkerDelayStatusRequest;
import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.scheduler.service.DelayInstanceService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
public class WorkerDelayStatusConsumer extends BaseConsumer<WorkerDelayStatusRequest> {
    public WorkerDelayStatusConsumer(Long id,
                                     Integer consumerCoreThreadNum,
                                     Integer consumerMaxThreadNum,
                                     String consumerThreadName,
                                     Integer pollSize,
                                     String pollThreadName,
                                     TaskQueue<WorkerDelayStatusRequest> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues, 2000L, 1000L);
    }

    @Override
    public void consume(Long id, List<WorkerDelayStatusRequest> tasks) {
        this.consumerExecutor.submit(new WorkerDelayStatusRunnable(tasks));
    }

    private static class WorkerDelayStatusRunnable implements Runnable {
        private final List<WorkerDelayStatusRequest> tasks;

        private WorkerDelayStatusRunnable(List<WorkerDelayStatusRequest> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            try {
                this.tasks.forEach(r -> OpenjobSpringContext.getBean(DelayInstanceService.class).handleConsumerDelayStatus(r));
            } catch (Throwable throwable) {
                log.error("Worker delay status consume failed!", throwable);
            }
        }
    }
}
