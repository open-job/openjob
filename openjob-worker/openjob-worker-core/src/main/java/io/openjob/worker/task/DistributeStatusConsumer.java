package io.openjob.worker.task;

import io.openjob.common.task.BaseConsumer;
import io.openjob.common.task.TaskQueue;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.exception.BatchUpdateStatusException;
import io.openjob.worker.master.DistributeStatusHandler;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class DistributeStatusConsumer extends BaseConsumer<ContainerTaskStatusRequest> {

    public DistributeStatusConsumer(Long id,
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
        this.consumerExecutor.submit(new DistributeTaskStatusRunnable(tasks));
    }

    private static class DistributeTaskStatusRunnable implements Runnable {
        private final List<ContainerTaskStatusRequest> statusList;

        private DistributeTaskStatusRunnable(List<ContainerTaskStatusRequest> statusList) {
            this.statusList = statusList;
        }

        @Override
        public void run() {
            statusList.stream().collect(Collectors.groupingBy(ContainerTaskStatusRequest::getStatus))
                    .forEach((status, requestList) -> {
                        List<Task> updateList = requestList.stream().map(r -> {
                            String taskUniqueId = r.getTaskUniqueId();
                            return new Task(taskUniqueId, r.getStatus(), r.getResult());
                        }).collect(Collectors.toList());

                        try {
                            TaskDAO.INSTANCE.batchUpdateStatusByTaskId(updateList, status);
                        } catch (BatchUpdateStatusException e) {
                            DistributeStatusHandler.handle(requestList);
                            log.warn("Batch update status failed, will be retry.");
                        }
                    });
        }
    }
}
