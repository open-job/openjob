package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
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

import java.util.List;
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
    public void dispatchTasks(List<MasterStartContainerRequest> startRequests, Boolean isFailover) {
        String workerAddress = WorkerUtil.selectOneWorker();
        String workerPath = WorkerUtil.getWorkerContainerActorPath(workerAddress);
        ActorSelection workerSelection = actorContext.actorSelection(workerPath);

        // Persist tasks.
        // Notice h2 write delay.
        this.persistTasks(workerAddress, startRequests, isFailover);

        // Switch running status.
        if (!this.running.get()) {
            this.running.set(true);
        }

        MasterBatchStartContainerRequest batchRequest = new MasterBatchStartContainerRequest();
        batchRequest.setJobId(this.jobInstanceDTO.getJobId());
        batchRequest.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        batchRequest.setStartContainerRequests(startRequests);

        try {
            FutureUtil.mustAsk(workerSelection, batchRequest, WorkerResponse.class, 3000L);
        } catch (Exception e) {
            List<Task> updateTaskList = startRequests.stream().map(m -> {
                Task task = new Task();
                task.setTaskId(m.getTaskUniqueId());
                task.setStatus(TaskStatusEnum.FAILOVER.getStatus());
                return task;
            }).collect(Collectors.toList());

            // Update task to failover.
            this.taskDAO.batchUpdateStatusByTaskId(updateTaskList, TaskStatusEnum.INIT.getStatus());

            log.warn("Dispatch task list failed! taskList={}", updateTaskList);
        }
    }

    protected void persistTasks(String workerAddress, List<MasterStartContainerRequest> startRequests, Boolean isFailover) {
        List<Task> taskList = startRequests.stream().map(m -> this.convertToTask(m, workerAddress)).collect(Collectors.toList());

        // Update task.
        if (isFailover) {
            List<String> taskIds = taskList.stream().map(Task::getTaskId).collect(Collectors.toList());
            this.taskDAO.batchUpdateStatusAndWorkerAddressByTaskId(taskIds, TaskStatusEnum.INIT.getStatus(), workerAddress);
            return;
        }

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

                this.taskMaster.dispatchTasks(startRequests, true);
            }
        }
    }
}
