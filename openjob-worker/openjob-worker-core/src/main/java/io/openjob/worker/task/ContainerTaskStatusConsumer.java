package io.openjob.worker.task;

import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import io.openjob.worker.request.ContainerTaskStatusRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class ContainerTaskStatusConsumer extends BaseConsumer<ContainerTaskStatusRequest> {

    public ContainerTaskStatusConsumer(Long id,
                                       Integer consumerCoreThreadNum,
                                       Integer consumerMaxThreadNum,
                                       String consumerThreadName,
                                       Integer pollSize,
                                       String pollThreadName,
                                       TaskQueue<ContainerTaskStatusRequest> queues) {
        super(id, consumerCoreThreadNum, consumerMaxThreadNum, consumerThreadName, pollSize, pollThreadName, queues);
    }

    @Override
    public void consume(Long id, List<ContainerTaskStatusRequest> tasks) {
        consumerExecutor.submit(new TaskStatusConsumerRunnable(tasks));
    }

    private static class TaskStatusConsumerRunnable implements Runnable {
        private final List<ContainerTaskStatusRequest> taskList;

        public TaskStatusConsumerRunnable(List<ContainerTaskStatusRequest> taskList) {
            this.taskList = taskList;
        }

        @Override
        public void run() {
            Map<Long, List<ContainerTaskStatusRequest>> groupTaskList = taskList.stream()
                    .collect(Collectors.groupingBy(ContainerTaskStatusRequest::getJobInstanceId));


            for (Map.Entry<Long, List<ContainerTaskStatusRequest>> entry : groupTaskList.entrySet()) {
                ContainerTaskStatusRequest firstTask = entry.getValue().get(0);
                ContainerBatchTaskStatusRequest batchRequest = new ContainerBatchTaskStatusRequest();
                batchRequest.setJobId(firstTask.getJobId());
                batchRequest.setJobInstanceId(firstTask.getJobInstanceId());
                batchRequest.setWorkerAddress(firstTask.getWorkerAddress());
                batchRequest.setMasterActorPath(firstTask.getMasterActorPath());
                batchRequest.setCircleId(firstTask.getCircleId());
                batchRequest.setTaskStatusList(entry.getValue());

                WorkerActorSystem.atLeastOnceDelivery(batchRequest, null);
            }
        }
    }
}
