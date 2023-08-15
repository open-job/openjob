package io.openjob.worker.master;

import akka.actor.ActorContext;
import com.google.common.collect.Sets;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.FailStatusEnum;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.JobInstanceStopEnum;
import io.openjob.common.constant.TaskConstant;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.request.WorkerJobInstanceStatusRequest;
import io.openjob.common.request.WorkerJobInstanceTaskBatchRequest;
import io.openjob.common.request.WorkerJobInstanceTaskRequest;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import io.openjob.worker.request.MasterDestroyContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.request.MasterStopContainerRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractTaskMaster implements TaskMaster {

    protected AtomicLong taskIdGenerator = new AtomicLong(0);

    protected AtomicLong circleIdGenerator = new AtomicLong(1);

    protected JobInstanceDTO jobInstanceDTO;

    protected ActorContext actorContext;

    protected String localWorkerAddress;

    protected String localContainerPath;

    /**
     * Container workers.
     */
    protected Set<String> containerWorkers = Sets.newConcurrentHashSet();

    /**
     * Task dao.
     */
    protected TaskDAO taskDAO = TaskDAO.INSTANCE;

    /**
     * Task running status.
     */
    protected AtomicBoolean running = new AtomicBoolean(false);

    /**
     * 0 = init
     * 1 = normal
     * 2 = timeout
     *
     * @see JobInstanceStopEnum#getType()
     */
    protected AtomicInteger stopping = new AtomicInteger(0);

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

        // Initialize.
        this.init();
    }

    protected void init() {
        // Second delay
        if (TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.circleIdGenerator.set(this.jobInstanceDTO.getCircleId());
        }
    }

    @Override
    public void completeTask() throws InterruptedException {
        // Do complete task.
        this.doCompleteTask();

        // Remove task from manager
        this.removeTaskFromManager();

        // Not second delay task.
        if (!TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.destroyTaskContainer();
            return;
        }

        // Circle second delay task.
        if (NumberUtils.INTEGER_ZERO.equals(this.stopping.get())) {
            this.circleSecondDelayTask();
        }
    }

    @Override
    public void updateStatus(ContainerBatchTaskStatusRequest batchRequest) {
        // Handle status
        DistributeStatusHandler.handle(batchRequest.getTaskStatusList());
    }

    @Override
    public Boolean getRunning() {
        return this.running.get();
    }

    @Override
    public void stop(Integer type) {
        // Update status
        this.stopping.set(type);

        // Remove from task master pool.
        TaskMasterPool.remove(this.jobInstanceDTO.getJobInstanceId());

        // Stop task container.
        this.containerWorkers.forEach(w -> {
            MasterStopContainerRequest request = new MasterStopContainerRequest();
            request.setJobId(this.jobInstanceDTO.getJobId());
            request.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
            request.setWorkerAddress(w);
            request.setStopType(type);
            WorkerActorSystem.atLeastOnceDelivery(request, null);
        });

        // Remove task from manager
        this.removeTaskFromManager();

        // Remove task from manager
        this.removeTaskFromManager();

        // Not second delay task.
        this.destroyTaskContainer();
    }

    @Override
    public void destroyTaskContainer() {
        // Remove from task master pool.
        TaskMasterPool.remove(this.jobInstanceDTO.getJobInstanceId());

        // Stop task container
        this.containerWorkers.forEach(w -> {
            MasterDestroyContainerRequest destroyRequest = new MasterDestroyContainerRequest();
            destroyRequest.setJobId(this.jobInstanceDTO.getJobId());
            destroyRequest.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
            destroyRequest.setWorkerAddress(w);
            WorkerActorSystem.atLeastOnceDelivery(destroyRequest, null);
        });
    }

    protected void addTask2Manager() {
        if (this.jobInstanceDTO.getExecuteTimeout() > 0) {
            TaskMasterManager.INSTANCE.addTask(this.jobInstanceDTO.getJobInstanceId(), DateUtil.timestamp() + this.jobInstanceDTO.getExecuteTimeout());
        }
    }

    protected void removeTaskFromManager() {
        if (this.jobInstanceDTO.getExecuteTimeout() > 0) {
            TaskMasterManager.INSTANCE.remove(this.jobInstanceDTO.getJobInstanceId());
        }
    }

    protected Long acquireTaskId() {
        return taskIdGenerator.incrementAndGet();
    }

    protected MasterStartContainerRequest getMasterStartContainerRequest() {
        MasterStartContainerRequest startReq = this.getJobMasterStartContainerRequest();
        startReq.setJobId(this.jobInstanceDTO.getJobId());
        startReq.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        startReq.setDispatchVersion(this.jobInstanceDTO.getDispatchVersion());
        startReq.setTaskId(this.acquireTaskId());
        startReq.setCircleId(circleIdGenerator.get());
        return startReq;
    }

    protected Task convertToTask(MasterStartContainerRequest startRequest, String workerAddress) {
        Task task = new Task();
        task.setJobId(startRequest.getJobId());
        task.setInstanceId(startRequest.getJobInstanceId());
        task.setDispatchVersion(this.jobInstanceDTO.getDispatchVersion());
        task.setCircleId(startRequest.getCircleId());
        task.setTaskId(startRequest.getTaskUniqueId());
        task.setTaskParentId(startRequest.getParentTaskUniqueId());
        task.setTaskName(startRequest.getTaskName());
        task.setStatus(TaskStatusEnum.INIT.getStatus());
        task.setWorkerAddress(workerAddress);
        return task;
    }

    protected MasterStartContainerRequest convertToMasterStartContainerRequest(Task task) {
        MasterStartContainerRequest containerRequest = this.getJobMasterStartContainerRequest();
        containerRequest.setJobId(task.getJobId());
        containerRequest.setJobInstanceId(task.getInstanceId());
        containerRequest.setDispatchVersion(this.jobInstanceDTO.getDispatchVersion());
        containerRequest.setTaskId(TaskUtil.getRandomUniqueIdLastId(task.getTaskId()));
        containerRequest.setParentTaskId(TaskUtil.getRandomUniqueIdLastId(task.getTaskParentId()));
        containerRequest.setCircleId(task.getCircleId());
        containerRequest.setTaskName(task.getTaskName());
        return containerRequest;
    }

    protected MasterStartContainerRequest getJobMasterStartContainerRequest() {
        MasterStartContainerRequest containerRequest = new MasterStartContainerRequest();
        containerRequest.setJobParamType(this.jobInstanceDTO.getJobParamType());
        containerRequest.setJobParams(this.jobInstanceDTO.getJobParams());
        containerRequest.setJobExtendParamsType(this.jobInstanceDTO.getJobExtendParamsType());
        containerRequest.setJobExtendParams(this.jobInstanceDTO.getJobExtendParams());
        containerRequest.setExecuteType(this.jobInstanceDTO.getExecuteType());
        containerRequest.setWorkflowId(this.jobInstanceDTO.getWorkflowId());
        containerRequest.setProcessorType(this.jobInstanceDTO.getProcessorType());
        containerRequest.setProcessorInfo(this.jobInstanceDTO.getProcessorInfo());
        containerRequest.setFailRetryInterval(this.jobInstanceDTO.getFailRetryInterval());
        containerRequest.setFailRetryTimes(this.jobInstanceDTO.getFailRetryTimes());
        containerRequest.setTimeExpression(this.jobInstanceDTO.getTimeExpression());
        containerRequest.setTimeExpressionType(this.jobInstanceDTO.getTimeExpressionType());
        containerRequest.setConcurrency(this.jobInstanceDTO.getConcurrency());
        containerRequest.setMasterAkkaPath(String.format("%s%s", this.localWorkerAddress, AkkaConstant.WORKER_PATH_TASK_MASTER));
        return containerRequest;
    }

    protected WorkerJobInstanceTaskRequest convertToTaskRequest(Task task) {
        WorkerJobInstanceTaskRequest taskRequest = new WorkerJobInstanceTaskRequest();
        taskRequest.setJobId(task.getJobId());
        taskRequest.setJobInstanceId(task.getInstanceId());
        taskRequest.setCircleId(task.getCircleId());
        taskRequest.setDispatchVersion(task.getDispatchVersion());
        taskRequest.setTaskId(task.getTaskId());
        taskRequest.setTaskName(task.getTaskName());
        taskRequest.setParentTaskId(task.getTaskParentId());
        taskRequest.setStatus(this.getTaskStatus(task.getStatus()));
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

    /**
     * Job instance status and tasks
     */
    protected void doCompleteTask() {
        // Second delay to do circle status
        if (!TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.doSecondCircleStatus();
        } else {
            // Job instance status
            this.doJobInstanceStatus();
        }

        // Job instance tasks
        this.doJobInstanceTasks();
    }

    protected void doSecondCircleStatus() {

    }

    protected void doJobInstanceStatus() {
        WorkerJobInstanceStatusRequest instanceRequest = new WorkerJobInstanceStatusRequest();
        instanceRequest.setCircleId(this.circleIdGenerator.get());
        instanceRequest.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        instanceRequest.setJobId(this.jobInstanceDTO.getJobId());
        instanceRequest.setStatus(this.getInstanceStatus());
        instanceRequest.setFailStatus(this.getFailStatus());
        WorkerActorSystem.atLeastOnceDelivery(instanceRequest, null);
    }

    @SuppressWarnings("all")
    protected void doJobInstanceTasks() {
        long size = 100;
        long page = CommonConstant.FIRST_PAGE;
        while (true) {
            List<Task> queryTask = TaskDAO.INSTANCE.getList(this.jobInstanceDTO.getJobInstanceId(), this.circleIdGenerator.get(), size);

            // Empty query.
            if (CollectionUtils.isEmpty(queryTask)) {
                break;
            }

            // Convert to `WorkerJobInstanceTaskRequest`
            List<WorkerJobInstanceTaskRequest> taskRequestList = queryTask.stream()
                    .map(this::convertToTaskRequest)
                    .collect(Collectors.toList());

            WorkerJobInstanceTaskBatchRequest workerJobInstanceTaskBatchRequest = new WorkerJobInstanceTaskBatchRequest();
            workerJobInstanceTaskBatchRequest.setTaskRequestList(taskRequestList);
            WorkerActorSystem.atLeastOnceDelivery(workerJobInstanceTaskBatchRequest, null);

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

    protected Integer getInstanceStatus() {
        // Normal stop
        if (JobInstanceStopEnum.isNormal(this.stopping.get())) {
            return InstanceStatusEnum.STOP.getStatus();
        }

        // Timeout stop
        if (JobInstanceStopEnum.isTimeout(this.stopping.get())) {
            return InstanceStatusEnum.FAIL.getStatus();
        }

        // Not stop
        long circleId = this.circleIdGenerator.get();
        long instanceId = this.jobInstanceDTO.getJobInstanceId();
        long failedCount = TaskDAO.INSTANCE.countTask(instanceId, circleId, Collections.singletonList(TaskStatusEnum.FAILED.getStatus()));
        return failedCount > 0 ? InstanceStatusEnum.FAIL.getStatus() : InstanceStatusEnum.SUCCESS.getStatus();
    }

    protected Integer getFailStatus() {
        return (JobInstanceStopEnum.isTimeout(this.stopping.get())) ? FailStatusEnum.EXECUTE_TIMEOUT.getStatus() : FailStatusEnum.NONE.getStatus();
    }

    protected Integer getTaskStatus(Integer status) {
        // Normal stop
        if (JobInstanceStopEnum.isNormal(this.stopping.get()) && TaskStatusEnum.isNotFinishStatus(status)) {
            return TaskStatusEnum.STOP.getStatus();
        }

        // Timeout stop
        if (JobInstanceStopEnum.isTimeout(this.stopping.get()) && TaskStatusEnum.isNotFinishStatus(status)) {
            return TaskStatusEnum.FAILED.getStatus();
        }
        return status;
    }

    protected void circleSecondDelayTask() throws InterruptedException {
        long instanceId = this.jobInstanceDTO.getJobInstanceId();

        // Second delay task.
        long delayTime = Long.parseLong(this.jobInstanceDTO.getTimeExpression());
        Thread.sleep(delayTime * 1000L);

        // Rest task id
        this.taskIdGenerator.set(0L);

        // Next circle id.
        long jobId = this.jobInstanceDTO.getJobId();
        long nextCircleId = this.circleIdGenerator.incrementAndGet();
        log.info("Second delay task begin jobId={} instanceId={} circleId={}", jobId, instanceId, nextCircleId);

        this.submit();
    }
}
