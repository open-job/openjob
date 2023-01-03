package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.MasterBatchStartContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractDistributeTaskMaster extends AbstractTaskMaster {

    protected ScheduledExecutorService scheduledService;

    public AbstractDistributeTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void submit() {

    }

    @Override
    protected void init() {
        super.init();

        this.scheduledService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-heartbeat-thread").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        // Check task complete status.
        this.scheduledService.scheduleWithFixedDelay(new AbstractDistributeTaskMaster.TaskStatusChecker(this), 1, 3L, TimeUnit.SECONDS);

        // Pull failover task to redispatch.
        this.scheduledService.scheduleWithFixedDelay(new AbstractDistributeTaskMaster.TaskFailoverPuller(this), 1, 3L, TimeUnit.SECONDS);
    }

    /**
     * Dispatch tasks.
     *
     * @param startRequests start requests.
     * @param isFailover    is failover
     */
    public void dispatchTasks(List<MasterStartContainerRequest> startRequests, Boolean isFailover, Set<String> failWorkers) {
        String workerAddress = WorkerUtil.selectOneWorker(failWorkers);
        if (Objects.isNull(workerAddress)) {
            log.error("Not available worker to dispatch! tasks={} failover={}", startRequests, isFailover);
            return;
        }

        try {
            this.doDispatchTasks(workerAddress, startRequests, isFailover);
        } catch (Throwable e) {
            // Add fail workers.
            failWorkers.add(workerAddress);

            // Select worker address.
            this.dispatchTasks(startRequests, isFailover, failWorkers);
        }
    }

    /**
     * Dispatch tasks.
     *
     * @param workerAddress worker dddress
     * @param startRequests start requests.
     * @param isFailover    is failover
     */
    public void doDispatchTasks(String workerAddress, List<MasterStartContainerRequest> startRequests, Boolean isFailover) {
        ActorSelection workerSelection = WorkerUtil.getWorkerContainerActor(workerAddress);

        // Add container workers.
        this.containerWorkers.add(workerAddress);

        // Not failover to persist tasks.
        if (!isFailover) {
            this.persistTasks(workerAddress, startRequests);
        }

        // Switch running status.
        if (!this.running.get()) {
            this.running.set(true);
        }

        MasterBatchStartContainerRequest batchRequest = new MasterBatchStartContainerRequest();
        batchRequest.setJobId(this.jobInstanceDTO.getJobId());
        batchRequest.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        batchRequest.setStartContainerRequests(startRequests);

        FutureUtil.mustAsk(workerSelection, batchRequest, WorkerResponse.class, 3000L);

        // Failover to update status.
        if (isFailover) {
            List<String> taskIds = startRequests.stream().map(MasterStartContainerRequest::getTaskUniqueId).collect(Collectors.toList());
            this.taskDAO.batchUpdateStatusAndWorkerAddressByTaskId(taskIds, TaskStatusEnum.INIT.getStatus(), workerAddress);
        }
    }

    protected void persistTasks(String workerAddress, List<MasterStartContainerRequest> startRequests) {
        List<Task> taskList = startRequests.stream().map(m -> this.convertToTask(m, workerAddress)).collect(Collectors.toList());

        // Batch add task.
        taskDAO.batchAdd(taskList);
    }

    protected JobContext getBaseJobContext() {
        JobContext jobContext = new JobContext();
        jobContext.setJobId(this.jobInstanceDTO.getJobId());
        jobContext.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        jobContext.setTaskId(this.acquireTaskId());
        jobContext.setJobParams(this.jobInstanceDTO.getJobParams());
        jobContext.setProcessorType(this.jobInstanceDTO.getProcessorType());
        jobContext.setProcessorInfo(this.jobInstanceDTO.getProcessorInfo());
        jobContext.setFailRetryInterval(this.jobInstanceDTO.getFailRetryInterval());
        jobContext.setFailRetryTimes(this.jobInstanceDTO.getFailRetryTimes());
        jobContext.setExecuteType(this.jobInstanceDTO.getExecuteType());
        jobContext.setConcurrency(this.jobInstanceDTO.getConcurrency());
        jobContext.setTimeExpression(this.jobInstanceDTO.getTimeExpression());
        jobContext.setTimeExpressionType(this.jobInstanceDTO.getTimeExpressionType());
        return jobContext;
    }

    protected static class TaskStatusChecker implements Runnable {
        private final AbstractDistributeTaskMaster taskMaster;

        public TaskStatusChecker(AbstractDistributeTaskMaster taskMaster) {
            this.taskMaster = taskMaster;
        }

        @Override
        public void run() {
            // When task is running to check status.
            if (!this.taskMaster.running.get()) {
                return;
            }

            long instanceId = this.taskMaster.jobInstanceDTO.getJobInstanceId();

            // Dispatch fail task.
            boolean isComplete = this.taskMaster.isTaskComplete(instanceId, taskMaster.circleIdGenerator.get());
            if (isComplete) {
                try {
                    this.taskMaster.completeTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // When task complete reset status.
                this.taskMaster.running.set(false);
            }
        }
    }

    protected static class TaskFailoverPuller implements Runnable {

        protected TaskDAO taskDAO = TaskDAO.INSTANCE;
        private final AbstractDistributeTaskMaster taskMaster;

        public TaskFailoverPuller(AbstractDistributeTaskMaster taskMaster) {
            this.taskMaster = taskMaster;
        }

        @Override
        public void run() {
            // When task is running to check status.
            if (!this.taskMaster.running.get()) {
                return;
            }

            long size = 100;
            long instanceId = this.taskMaster.jobInstanceDTO.getJobInstanceId();
            while (true) {
                List<Task> taskList = this.taskDAO.pullFailoverListBySize(instanceId, size);
                if (CollectionUtils.isEmpty(taskList)) {
                    break;
                }

                List<MasterStartContainerRequest> startRequests = taskList.stream()
                        .map(this.taskMaster::convertToMasterStartContainerRequest)
                        .collect(Collectors.toList());

                try {
                    this.taskMaster.dispatchTasks(startRequests, true, Collections.emptySet());
                } catch (Throwable e) {
                    log.error("Task failover dispatch task failed! message={}", e.getMessage());
                }
            }
        }
    }
}
