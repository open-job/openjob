package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.request.WorkerJobInstanceStatusRequest;
import io.openjob.common.request.WorkerJobInstanceTaskRequest;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
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

        this.init();
    }

    protected void init() {

    }

    @Override
    public void completeTask() throws InterruptedException {
        // Do complete task.
        this.doCompleteTask();

        // Not second delay task.
        if (!TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.stop();
            return;
        }

        // Circle second delay task.
        this.circleSecondDelayTask();
    }

    @Override
    public void updateStatus(ContainerBatchTaskStatusRequest batchRequest) {
        // Update list
        List<Task> updateList = new ArrayList<>();
        for (ContainerTaskStatusRequest statusRequest : batchRequest.getTaskStatusList()) {
            String taskUniqueId = statusRequest.getTaskUniqueId();
            updateList.add(new Task(taskUniqueId, statusRequest.getStatus()));
        }

        // Group by status
        Map<Integer, List<Task>> groupList = updateList.stream().collect(Collectors.groupingBy(Task::getStatus));

        // Update by group
        for (Map.Entry<Integer, List<Task>> entry : groupList.entrySet()) {
            taskDAO.batchUpdateStatusByTaskId(entry.getValue(), entry.getKey());
        }

        // Not MR to complete task.
        if (!ExecuteTypeEnum.MAP_REDUCE.getType().equals(this.jobInstanceDTO.getExecuteType())) {
            try {
                this.completeTask();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
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

    protected WorkerJobInstanceTaskRequest convertToTaskRequest(Task task) {
        WorkerJobInstanceTaskRequest taskRequest = new WorkerJobInstanceTaskRequest();
        taskRequest.setJobId(task.getJobId());
        return taskRequest;
    }

    protected Boolean isTaskComplete(Long instanceId, Long circleId) {
        return taskDAO.countTask(instanceId, circleId, TaskStatusEnum.NON_FINISH_LIST) == 0;
    }

    protected void doCompleteTask() {
        long circleId = this.circleIdGenerator.get();
        long instanceId = this.jobInstanceDTO.getJobInstanceId();

        long size = 100;
        while (true) {
            List<Task> queryTask = TaskDAO.INSTANCE.getList(instanceId, circleId, 1L, size);

            // Query complete.
            if (queryTask.size() < size) {
                break;
            }

            // Convert to `WorkerJobInstanceTaskRequest`
            List<WorkerJobInstanceTaskRequest> taskRequestList = queryTask.stream()
                    .map(this::convertToTaskRequest)
                    .collect(Collectors.toList());

            WorkerJobInstanceStatusRequest instanceStatusRequest = new WorkerJobInstanceStatusRequest();
            instanceStatusRequest.setCircleId(circleId);
            instanceStatusRequest.setJobInstanceId(instanceId);
            instanceStatusRequest.setJobId(this.jobInstanceDTO.getJobId());
            instanceStatusRequest.setTaskRequestList(taskRequestList);

            OpenjobWorker.atLeastOnceDelivery(instanceStatusRequest, null);

            // Delete tasks.
            List<String> deleteTaskIds = queryTask.stream().map(Task::getTaskId).collect(Collectors.toList());
            taskDAO.batchDeleteByTaskIds(deleteTaskIds);
        }
    }

    protected void circleSecondDelayTask() throws InterruptedException {
        long instanceId = this.jobInstanceDTO.getJobInstanceId();

        // Second delay task.
        long delayTime = Long.parseLong(this.jobInstanceDTO.getTimeExpression());
        Thread.sleep(delayTime * 1000L);

        // Next circle id.
        long jobId = this.jobInstanceDTO.getJobId();
        long nextCircleId = this.circleIdGenerator.incrementAndGet();
        log.info("Second delay task begin jobId={} instanceId={} circleId={}", jobId, instanceId, nextCircleId);

        this.submit();
    }
}
