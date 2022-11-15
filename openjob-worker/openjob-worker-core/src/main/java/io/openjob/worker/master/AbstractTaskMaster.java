package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.common.constant.CommonConst;
import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.common.constant.InstanceStatusEnum;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractTaskMaster implements TaskMaster {

    protected AtomicLong taskIdGenerator = new AtomicLong(0);

    protected AtomicLong circleIdGenerator = new AtomicLong(0);

    protected JobInstanceDTO jobInstanceDTO;

    protected ActorContext actorContext;

    protected String localWorkerAddress;

    protected String localContainerPath;

    protected List<String> workerAddresses;

    /**
     * Task dao.
     */
    protected TaskDAO taskDAO = TaskDAO.INSTANCE;

    /**
     * New AbstractTaskMaster
     *
     * @param jobInstanceDTO job instance context.
     * @param actorContext   actor context.
     */
    public AbstractTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        this.jobInstanceDTO = jobInstanceDTO;
        this.actorContext = actorContext;
        this.localWorkerAddress = actorContext.provider().addressString();
        this.localContainerPath = actorContext.provider().getDefaultAddress().toString() + WorkerAkkaConstant.PATH_TASK_CONTAINER;
        this.workerAddresses = jobInstanceDTO.getWorkerAddresses();

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
        boolean isStandalone = ExecuteTypeEnum.STANDALONE.getType().equals(this.jobInstanceDTO.getExecuteType());
        if (isStandalone && this.isTaskComplete(this.jobInstanceDTO.getJobInstanceId(), this.circleIdGenerator.get())) {
            try {
                this.completeTask();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        // Remove from task master pool.
        TaskMasterPool.remove(this.jobInstanceDTO.getJobInstanceId());
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
        startReq.setCircleId(circleIdGenerator.get());
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
        taskRequest.setJobInstanceId(task.getInstanceId());
        taskRequest.setCircleId(task.getCircleId());
        taskRequest.setTaskId(task.getTaskId());
        taskRequest.setTaskName(task.getTaskName());
        taskRequest.setParentTaskId(task.getTaskParentId());
        taskRequest.setStatus(task.getStatus());
        taskRequest.setResult(task.getResult());
        taskRequest.setWorkerAddress(task.getWorkerAddress());
        taskRequest.setCreateTime(task.getCreateTime());
        taskRequest.setUpdateTime(task.getUpdateTime());
        return taskRequest;
    }

    protected Boolean isTaskComplete(Long instanceId, Long circleId) {
        Integer nonFinishCount = taskDAO.countTask(instanceId, circleId, TaskStatusEnum.NON_FINISH_LIST);
        return nonFinishCount <= 0;
    }

    protected void doCompleteTask() {
        long circleId = this.circleIdGenerator.get();
        long instanceId = this.jobInstanceDTO.getJobInstanceId();

        // Failed task count.
        long failedCount = TaskDAO.INSTANCE.countTask(instanceId, circleId, Collections.singletonList(TaskStatusEnum.FAILED.getStatus()));
        int instanceStatus = failedCount > 0 ? InstanceStatusEnum.FAIL.getStatus() : InstanceStatusEnum.SUCCESS.getStatus();

        long size = 100;
        long page = CommonConst.FIRST_PAGE;
        while (true) {
            List<Task> queryTask = TaskDAO.INSTANCE.getList(instanceId, circleId, size);

            // Empty query.
            if (CollectionUtils.isEmpty(queryTask)) {
                break;
            }

            // Convert to `WorkerJobInstanceTaskRequest`
            List<WorkerJobInstanceTaskRequest> taskRequestList = queryTask.stream()
                    .map(this::convertToTaskRequest)
                    .collect(Collectors.toList());

            WorkerJobInstanceStatusRequest instanceRequest = new WorkerJobInstanceStatusRequest();
            instanceRequest.setCircleId(circleId);
            instanceRequest.setJobInstanceId(instanceId);
            instanceRequest.setJobId(this.jobInstanceDTO.getJobId());
            instanceRequest.setStatus(instanceStatus);
            instanceRequest.setTaskRequestList(taskRequestList);
            instanceRequest.setPage(page);

            OpenjobWorker.atLeastOnceDelivery(instanceRequest, null);

            // Delete tasks.
            List<String> deleteTaskIds = queryTask.stream().map(Task::getTaskId).collect(Collectors.toList());
            taskDAO.batchDeleteByTaskIds(deleteTaskIds);

            // Next page.
            page++;

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                log.error("DoCompleteTask sleep error!", e);
            }

            // Query complete.
            if (queryTask.size() < size) {
                break;
            }
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
