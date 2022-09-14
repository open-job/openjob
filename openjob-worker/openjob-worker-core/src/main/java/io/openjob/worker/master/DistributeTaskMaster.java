package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.MasterBatchStartContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.WorkerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class DistributeTaskMaster extends AbstractTaskMaster {

    protected ScheduledExecutorService scheduledService;

    public DistributeTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
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

        // Check task status.
        this.scheduledService.scheduleWithFixedDelay(new DistributeTaskMaster.TaskStatusChecker(this), 1, 3L, TimeUnit.SECONDS);

        // Check task container alive
        this.scheduledService.scheduleWithFixedDelay(new DistributeTaskMaster.TaskContainerChecker(), 1, 3L, TimeUnit.SECONDS);
    }

    public void dispatchTasks(List<MasterStartContainerRequest> startRequests) {
        String workerAddress = this.jobInstanceDTO.getWorkerAddresses().get(0);
        String workerPath = WorkerUtil.getWorkerContainerActorPath(workerAddress);
        ActorSelection workerSelection = actorContext.actorSelection(workerPath);

        // Persist tasks.
        this.persistTasks(startRequests);

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
        List<Task> taskList = new ArrayList<>();
        startRequests.forEach(sr -> taskList.add(this.convertToTask(sr)));

        taskDAO.batchAdd(taskList);
    }

    protected static class TaskStatusChecker implements Runnable {
        private final DistributeTaskMaster taskMaster;

        public TaskStatusChecker(DistributeTaskMaster taskMaster) {
            this.taskMaster = taskMaster;
        }

        @Override
        public void run() {
            long instanceId = this.taskMaster.jobInstanceDTO.getJobInstanceId();

            // Dispatch fail task.

            boolean isComplete = this.taskMaster.isTaskComplete(instanceId, taskMaster.circleIdGenerator.get());
            if (isComplete) {
                try {
                    this.taskMaster.completeTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected static class TaskContainerChecker implements Runnable {
        @Override
        public void run() {

        }
    }
}
