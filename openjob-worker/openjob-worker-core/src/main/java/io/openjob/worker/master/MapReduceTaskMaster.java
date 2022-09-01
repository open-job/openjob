package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.MasterBatchStartContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.task.MapReduceTaskConsumer;
import io.openjob.worker.task.TaskQueue;
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
public class MapReduceTaskMaster extends BaseTaskMaster {

    /**
     * Child tasks.
     */
    protected TaskQueue<MasterStartContainerRequest> childTaskQueue;

    protected MapReduceTaskConsumer<MasterStartContainerRequest> childTaskConsumer;

    protected ScheduledExecutorService scheduledService;

    public MapReduceTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    protected void init() {
        childTaskQueue = new TaskQueue<>(this.jobInstanceDTO.getJobInstanceId(), 10240);
        childTaskConsumer = new MapReduceTaskConsumer<>(
                this.jobInstanceDTO.getJobInstanceId(),
                1,
                1,
                "Openjob-mapreduce-consumer",
                100,
                "Openjob-mapreduce-consumer-poll",
                childTaskQueue
        );

        childTaskConsumer.start();

        this.scheduledService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-heartbeat-thread").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        // Check task status.
        this.scheduledService.scheduleWithFixedDelay(new TaskStatusChecker(this), 1, 3L, TimeUnit.SECONDS);

        // Check task container alive
        this.scheduledService.scheduleWithFixedDelay(new TaskContainerChecker(), 1, 3L, TimeUnit.SECONDS);
    }

    public void map(List<byte[]> tasks, String taskName) {
        try {
            for (byte[] task : tasks) {
                MasterStartContainerRequest startReq = this.getMasterStartContainerRequest();
                startReq.setTask(task);
                startReq.setTaskName(taskName);
                childTaskQueue.submit(startReq);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void submit() {
        MasterStartContainerRequest masterStartContainerRequest = this.getMasterStartContainerRequest();
        masterStartContainerRequest.setTaskName(WorkerConstant.MAP_TASK_ROOT_NAME);
        ArrayList<MasterStartContainerRequest> startRequests = Lists.newArrayList(masterStartContainerRequest);

        this.dispatchTasks(startRequests);
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

    /**
     * Use checker thread to complete task for MR
     */
    @Override
    public void completeTask() {

    }

    @Override
    public void stop() {
        // Stop child task consumer
        this.childTaskConsumer.stop();

        // Stop scheduled thread poll
        this.scheduledService.shutdownNow();

        // Stop task container.

        // Remove from task master pool.
        TaskMasterPool.remove(this.jobInstanceDTO.getJobInstanceId());
    }

    protected void persistTasks(List<MasterStartContainerRequest> startRequests) {
        List<Task> taskList = new ArrayList<>();
        startRequests.forEach(sr -> taskList.add(this.convertToTask(sr)));

        taskDAO.batchAdd(taskList);
    }

    protected static class TaskStatusChecker implements Runnable {
        private final MapReduceTaskMaster taskMaster;

        public TaskStatusChecker(MapReduceTaskMaster taskMaster) {
            this.taskMaster = taskMaster;
        }

        @Override
        public void run() {
            long jobId = this.taskMaster.jobInstanceDTO.getJobId();
            long instanceId = this.taskMaster.jobInstanceDTO.getJobInstanceId();

            // Dispatch fail task.

            boolean isComplete = this.taskMaster.isTaskComplete(instanceId, taskMaster.circleIdGenerator.get());

            if (isComplete && !this.taskMaster.childTaskConsumer.isActive()) {
                this.taskMaster.completeTask();
            }
        }
    }


    protected static class TaskContainerChecker implements Runnable {
        @Override
        public void run() {

        }
    }
}
