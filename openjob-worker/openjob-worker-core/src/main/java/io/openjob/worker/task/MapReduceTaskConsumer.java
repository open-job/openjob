package io.openjob.worker.task;

import io.openjob.worker.master.MapReduceTaskMaster;
import io.openjob.worker.master.TaskMasterPool;
import io.openjob.worker.request.MasterStartContainerRequest;
import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class MapReduceTaskConsumer<T> extends BaseConsumer<T> {
    public MapReduceTaskConsumer(Long id,
                                 Integer handlerCoreThreadNum,
                                 Integer handlerMaxThreadNum,
                                 String handlerThreadName,
                                 Integer pollSize,
                                 String pollThreadName,
                                 TaskQueue<T> queues) {
        super(id, handlerCoreThreadNum, handlerMaxThreadNum, handlerThreadName, pollSize, pollThreadName, queues);
    }


    @Override
    public void consume(Long jobInstanceId, List<T> tasks) {
        this.consumerExecutor.submit(new MapReduceTaskRunnable(jobInstanceId, tasks));
    }

    @Data
    private class MapReduceTaskRunnable implements Runnable {
        private Long jobInstanceId;
        private List<T> taskList;

        public MapReduceTaskRunnable(Long jobInstanceId, List<T> taskList) {
            this.jobInstanceId = jobInstanceId;
            this.taskList = taskList;
        }

        @Override
        public void run() {
            MapReduceTaskMaster taskMaster = (MapReduceTaskMaster) TaskMasterPool.get(this.jobInstanceId);
            taskMaster.dispatchTasks((List<MasterStartContainerRequest>) this.taskList);
        }
    }
}
