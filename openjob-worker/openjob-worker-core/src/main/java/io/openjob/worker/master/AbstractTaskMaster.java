package io.openjob.worker.master;

import akka.actor.ActorContext;
import com.google.common.collect.Sets;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.request.WorkerJobInstanceStatusRequest;
import io.openjob.common.request.WorkerJobInstanceTaskRequest;
import io.openjob.common.util.TaskUtil;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.init.WorkerActorSystem;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import io.openjob.worker.request.MasterDestroyContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
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
        // Distribute task.
        // Submit to queue.
        if (!(this instanceof StandaloneTaskMaster)) {
            DistributeStatusHandler.handle(batchRequest.getTaskStatusList());
            return;
        }

        // Update list
        List<Task> updateList = batchRequest.getTaskStatusList().stream().map(s -> {
            String taskUniqueId = s.getTaskUniqueId();
            return new Task(taskUniqueId, s.getStatus());
        }).collect(Collectors.toList());

        // Update by status.
        updateList.stream().collect(Collectors.groupingBy(Task::getStatus))
                .forEach((status, groupList) -> taskDAO.batchUpdateStatusByTaskId(groupList, status));

        // Standalone task.
        // Do Complete task.
        if (this.isTaskComplete(this.jobInstanceDTO.getJobInstanceId(), this.circleIdGenerator.get())) {
            try {
                this.completeTask();
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    @Override
    public Boolean getRunning() {
        return this.running.get();
    }

    @Override
    public void stop() {
        // Remove from task master pool.
        TaskMasterPool.remove(this.jobInstanceDTO.getJobInstanceId());

        // Destroy task container.
        this.destroyTaskContainer();
    }

    @Override
    public void destroyTaskContainer() {
        this.containerWorkers.forEach(w -> {
            MasterDestroyContainerRequest destroyRequest = new MasterDestroyContainerRequest();
            destroyRequest.setJobId(this.jobInstanceDTO.getJobId());
            destroyRequest.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
            destroyRequest.setWorkerAddress(w);
            WorkerActorSystem.atLeastOnceDelivery(destroyRequest, null);
        });
    }

    protected Long acquireTaskId() {
        return taskIdGenerator.getAndIncrement();
    }

    protected MasterStartContainerRequest getMasterStartContainerRequest() {
        MasterStartContainerRequest startReq = this.getJobMasterStartContainerRequest();
        startReq.setJobId(this.jobInstanceDTO.getJobId());
        startReq.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        startReq.setTaskId(this.acquireTaskId());
        startReq.setCircleId(circleIdGenerator.get());
        return startReq;
    }

    protected Task convertToTask(MasterStartContainerRequest startRequest, String workerAddress) {
        Task task = new Task();
        task.setJobId(startRequest.getJobId());
        task.setInstanceId(startRequest.getJobInstanceId());
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
        containerRequest.setTaskId(TaskUtil.getRandomUniqueIdLastId(task.getTaskId()));
        containerRequest.setParentTaskId(TaskUtil.getRandomUniqueIdLastId(task.getTaskParentId()));
        containerRequest.setCircleId(task.getCircleId());
        containerRequest.setTaskName(task.getTaskName());
        return containerRequest;
    }

    protected MasterStartContainerRequest getJobMasterStartContainerRequest() {
        MasterStartContainerRequest containerRequest = new MasterStartContainerRequest();
        containerRequest.setJobParams(this.jobInstanceDTO.getJobParams());
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

    @SuppressWarnings("all")
    protected void doCompleteTask() {
        long circleId = this.circleIdGenerator.get();
        long instanceId = this.jobInstanceDTO.getJobInstanceId();

        // Failed task count.
        long failedCount = TaskDAO.INSTANCE.countTask(instanceId, circleId, Collections.singletonList(TaskStatusEnum.FAILED.getStatus()));
        int instanceStatus = failedCount > 0 ? InstanceStatusEnum.FAIL.getStatus() : InstanceStatusEnum.SUCCESS.getStatus();

        long size = 100;
        long page = CommonConstant.FIRST_PAGE;
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

            WorkerActorSystem.atLeastOnceDelivery(instanceRequest, null);

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
