package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import com.google.common.collect.Lists;
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

    public MapReduceTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);

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
    }

    @Override
    protected void init() {

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
    public Boolean isTaskComplete(Long instanceId, Long circleId) {
        return super.isTaskComplete(instanceId, circleId) && !this.childTaskConsumer.isActive();
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

    @Override
    public void stop() {

    }

    private void persistTasks(List<MasterStartContainerRequest> startRequests) {
        List<Task> taskList = new ArrayList<>();
        startRequests.forEach(sr -> taskList.add(this.convertToTask(sr)));

        taskDAO.batchAdd(taskList);
    }
}
