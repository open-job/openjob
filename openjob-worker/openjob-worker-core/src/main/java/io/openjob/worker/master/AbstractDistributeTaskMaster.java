package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.MasterBatchStartContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.WorkerUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
public abstract class AbstractDistributeTaskMaster extends AbstractTaskMaster {

    protected ScheduledExecutorService scheduledService;

    public AbstractDistributeTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
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

        // Pull exception task to redispatch.
        this.scheduledService.scheduleWithFixedDelay(new AbstractDistributeTaskMaster.TaskExceptionPuller(this), 1, 3L, TimeUnit.SECONDS);
    }

    /**
     * Dispatch tasks.
     *
     * @param startRequests start requests.
     */
    public void dispatchTasks(List<MasterStartContainerRequest> startRequests) {
        String workerAddress = WorkerUtil.selectOneWorker();
        String workerPath = WorkerUtil.getWorkerContainerActorPath(workerAddress);
        ActorSelection workerSelection = actorContext.actorSelection(workerPath);

        // Persist tasks.
        // Notice h2 write delay.
        this.persistTasks(startRequests);

        // Switch running status.
        if (!this.running.get()) {
            this.running.set(true);
        }

        MasterBatchStartContainerRequest batchRequest = new MasterBatchStartContainerRequest();
        batchRequest.setJobId(this.jobInstanceDTO.getJobId());
        batchRequest.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        batchRequest.setStartContainerRequests(startRequests);

        try {
            FutureUtil.ask(workerSelection, batchRequest, 10L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void persistTasks(List<MasterStartContainerRequest> startRequests) {
        List<Task> taskList = startRequests.stream().map(this::convertToTask).collect(Collectors.toList());
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

    protected static class TaskExceptionPuller implements Runnable {

        protected TaskDAO taskDAO = TaskDAO.INSTANCE;
        private final AbstractDistributeTaskMaster taskMaster;

        public TaskExceptionPuller(AbstractDistributeTaskMaster taskMaster) {
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
                List<Task> taskList = this.taskDAO.pullExceptionListBySize(instanceId, size);
                if (CollectionUtils.isEmpty(taskList)) {
                    break;
                }
            }
        }
    }
}
