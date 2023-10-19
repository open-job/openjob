package io.openjob.server.cluster.task;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.dto.WorkerJobInstanceStatusReqDTO;
import io.openjob.server.cluster.manager.JobInstanceManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
public class WorkerJobInstanceConsumer extends BaseConsumer<WorkerJobInstanceStatusReqDTO> {
    public WorkerJobInstanceConsumer(Long id,
                                     Integer consumerCoreThreadNum,
                                     Integer consumerMaxThreadNum,
                                     String consumerThreadName,
                                     Integer pollSize,
                                     String pollThreadName,
                                     TaskQueue<WorkerJobInstanceStatusReqDTO> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues, 1000L, 1000L);
    }

    @Override
    public void consume(Long id, List<WorkerJobInstanceStatusReqDTO> tasks) {
        this.consumerExecutor.submit(new WorkerJobInstanceConsumerRunnable(tasks));
    }

    private static class WorkerJobInstanceConsumerRunnable implements Runnable {
        private final List<WorkerJobInstanceStatusReqDTO> tasks;

        private WorkerJobInstanceConsumerRunnable(List<WorkerJobInstanceStatusReqDTO> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            try {
                OpenjobSpringContext.getBean(JobInstanceManager.class).handleConsumerInstanceStatus(tasks);
            } catch (Throwable throwable) {
                log.error("Handler consumer instance status failed!", throwable);
            }
        }
    }
}
