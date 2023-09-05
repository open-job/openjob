package io.openjob.worker.task;

import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.worker.master.MapReduceTaskMaster;
import io.openjob.worker.master.TaskMasterPool;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.request.ProcessorMapTaskRequest;

import java.util.Collections;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class MapReduceTaskConsumer extends BaseConsumer<ProcessorMapTaskRequest> {
    public MapReduceTaskConsumer(Long id,
                                 Integer handlerCoreThreadNum,
                                 Integer handlerMaxThreadNum,
                                 String handlerThreadName,
                                 Integer pollSize,
                                 String pollThreadName,
                                 TaskQueue<ProcessorMapTaskRequest> queues) {
        super(id, handlerCoreThreadNum, handlerMaxThreadNum, handlerThreadName, pollSize, pollThreadName, queues);
    }


    @Override
    public void consume(Long jobInstanceId, List<ProcessorMapTaskRequest> tasks) {
        this.consumerExecutor.submit(new MapReduceTaskRunnable(this, jobInstanceId, tasks));
    }

    private static class MapReduceTaskRunnable implements Runnable {
        private final Long jobInstanceId;
        private final List<ProcessorMapTaskRequest> taskList;

        private final MapReduceTaskConsumer consumer;

        public MapReduceTaskRunnable(MapReduceTaskConsumer consumer, Long jobInstanceId, List<ProcessorMapTaskRequest> taskList) {
            this.jobInstanceId = jobInstanceId;
            this.taskList = taskList;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            MapReduceTaskMaster taskMaster = (MapReduceTaskMaster) TaskMasterPool.get(this.jobInstanceId);
            this.taskList.forEach(taskMaster::doMap);
            this.consumer.getActivePollNum().decrementAndGet();
        }
    }
}
