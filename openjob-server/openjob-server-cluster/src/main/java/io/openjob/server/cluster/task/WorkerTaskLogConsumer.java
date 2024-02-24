package io.openjob.server.cluster.task;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskLogReqDTO;
import io.openjob.server.cluster.manager.JobInstanceTaskLogManager;
import io.openjob.server.cluster.service.JobInstanceTaskLogService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.3
 */
@Slf4j
public class WorkerTaskLogConsumer extends BaseConsumer<WorkerJobInstanceTaskLogReqDTO> {

    public WorkerTaskLogConsumer(Long id,
                                 Integer consumerCoreThreadNum,
                                 Integer consumerMaxThreadNum,
                                 String consumerThreadName,
                                 Integer pollSize,
                                 String pollThreadName,
                                 TaskQueue<WorkerJobInstanceTaskLogReqDTO> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues, 1000L, 1000L);
    }

    @Override
    public void consume(Long id, List<WorkerJobInstanceTaskLogReqDTO> tasks) {
        this.consumerExecutor.submit(new WorkerTaskLogRunnable(tasks));
    }

    private static class WorkerTaskLogRunnable implements Runnable {
        private final List<WorkerJobInstanceTaskLogReqDTO> tasks;

        private WorkerTaskLogRunnable(List<WorkerJobInstanceTaskLogReqDTO> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            try {
                OpenjobSpringContext.getBean(JobInstanceTaskLogManager.class).batchInstanceTaskLog(this.tasks);
            } catch (Throwable throwable) {
                log.error("Job instance log failed!", throwable);
            }
        }
    }
}
