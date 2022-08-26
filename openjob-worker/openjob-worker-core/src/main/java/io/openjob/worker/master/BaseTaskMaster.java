package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.request.MasterStartContainerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class BaseTaskMaster implements TaskMaster {

    protected AtomicLong taskIdGenerator = new AtomicLong(0);

    protected AtomicLong circleIdGenerator = new AtomicLong(0);

    protected JobInstanceDTO jobInstanceDTO;
    protected ActorContext actorContext;
    protected String localWorkerAddress;
    protected String localContainerPath;

    /**
     * Task dao.
     */
    protected TaskDAO taskDAO = TaskDAO.INSTANCE;

    public BaseTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        this.jobInstanceDTO = jobInstanceDTO;
        this.actorContext = actorContext;
        this.localWorkerAddress = actorContext.provider().addressString();
        this.localContainerPath = actorContext.provider().getDefaultAddress().toString() + WorkerAkkaConstant.PATH_TASK_CONTAINER;
    }

    protected void init() {

    }

    @Override
    public Boolean isTaskComplete(Long instanceId, Long circleId) {
        return taskDAO.countTask(instanceId, circleId, TaskStatusEnum.NON_FINISH_LIST) == 0;
    }

    @Override
    public void updateStatus(ContainerBatchTaskStatusRequest batchRequest) {
        for (ContainerTaskStatusRequest statusRequest : batchRequest.getTaskStatusList()) {
            String taskUniqueId = statusRequest.getTaskUniqueId();
            List<Task> updateList = new ArrayList<>();
            updateList.add(new Task(taskUniqueId, statusRequest.getStatus()));
            taskDAO.batchUpdateStatusByTaskId(updateList);
        }

        // Task complete.
        if (this.isTaskComplete(batchRequest.getJobInstanceId(), batchRequest.getCircleId())) {
            System.out.printf("task complete jobId=%s instanceId=%s%n", batchRequest.getJobId(), batchRequest.getJobInstanceId());
        }
    }

    protected Long acquireTaskId() {
        return taskIdGenerator.getAndIncrement();
    }

    protected MasterStartContainerRequest getMasterStartContainerRequest() {
        MasterStartContainerRequest startReq = new MasterStartContainerRequest();
        startReq.setJobId(this.jobInstanceDTO.getJobId());
        startReq.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        startReq.setTaskId(this.acquireTaskId());
        startReq.setJobParams(this.jobInstanceDTO.getJobParams());
        startReq.setExecuteType(this.jobInstanceDTO.getExecuteType());
        startReq.setWorkflowId(this.jobInstanceDTO.getWorkflowId());
        startReq.setProcessorType(this.jobInstanceDTO.getProcessorType());
        startReq.setProcessorInfo(this.jobInstanceDTO.getProcessorInfo());
        startReq.setFailRetryInterval(this.jobInstanceDTO.getFailRetryInterval());
        startReq.setFailRetryTimes(this.jobInstanceDTO.getFailRetryTimes());
        startReq.setTimeExpression(this.jobInstanceDTO.getTimeExpression());
        startReq.setTimeExpressionType(this.jobInstanceDTO.getTimeExpressionType());
        startReq.setConcurrency(this.jobInstanceDTO.getConcurrency());
        startReq.setMasterAkkaPath(this.localContainerPath);
        startReq.setWorkerAddresses(this.jobInstanceDTO.getWorkerAddresses());
        startReq.setMasterAkkaPath(String.format("%s%s", this.localWorkerAddress, AkkaConstant.WORKER_PATH_TASK_MASTER));
        return startReq;
    }

    protected Task convertToTask(MasterStartContainerRequest startRequest) {
        Task task = new Task();
        task.setJobId(startRequest.getJobId());
        task.setInstanceId(startRequest.getJobInstanceId());
        task.setCircleId(startRequest.getCircleId());
        task.setTaskId(startRequest.getTaskUniqueId());
        task.setTaskParentId(startRequest.getParentTaskUniqueId());
        task.setTaskName(startRequest.getTaskName());
        task.setStatus(TaskStatusEnum.INIT.getStatus());
        task.setWorkerAddress(this.localWorkerAddress);
        return task;
    }
}
