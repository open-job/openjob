package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.common.constant.TaskConstant;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.task.TaskQueue;
import io.openjob.common.util.TaskUtil;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dao.TaskDAO;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.processor.MapReduceProcessor;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.processor.ProcessorHandler;
import io.openjob.worker.processor.TaskResult;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.request.ProcessorMapTaskRequest;
import io.openjob.worker.task.MapReduceTaskConsumer;
import io.openjob.worker.util.AddressUtil;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class MapReduceTaskMaster extends AbstractDistributeTaskMaster {

    /**
     * Child tasks.
     */
    protected TaskQueue<MasterStartContainerRequest> childTaskQueue;

    protected MapReduceTaskConsumer childTaskConsumer;


    public MapReduceTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    protected void init() {
        super.init();

        childTaskQueue = new TaskQueue<>(this.jobInstanceDTO.getJobInstanceId(), 10240);
        childTaskConsumer = new MapReduceTaskConsumer(
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
    public void completeTask() throws InterruptedException {
        // Reduce task.
        this.reduce();

        // Complete task.
        super.completeTask();
    }

    /**
     * Map operation.
     *
     * @param mapTaskReq mapTaskReq
     */
    public void map(ProcessorMapTaskRequest mapTaskReq) {
        try {
            for (byte[] task : mapTaskReq.getTasks()) {
                MasterStartContainerRequest startReq = this.getMasterStartContainerRequest();
                startReq.setTask(task);
                startReq.setTaskName(mapTaskReq.getTaskName());
                startReq.setParentTaskId(mapTaskReq.getTaskId());
                childTaskQueue.submit(startReq);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void doSubmit() {
        // Second delay to persist circle task
        if (TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.persistCircleTask();
        }

        // Dispatch tasks
        MasterStartContainerRequest masterStartContainerRequest = this.getMasterStartContainerRequest();
        masterStartContainerRequest.setTaskName(TaskConstant.MAP_TASK_ROOT_NAME);
        this.dispatchTasks(Collections.singletonList(masterStartContainerRequest), false, Collections.emptySet());

        // Add task manager
        this.addTask2Manager();
    }

    @Override
    protected Boolean isTaskComplete(Long instanceId, Long circleId) {
        // Must first to check consumer active.
        return !this.childTaskConsumer.isActive() && super.isTaskComplete(instanceId, circleId);
    }

    @Override
    public void stop(Integer type) {
        // Stop task container.

        // Stop child task consumer
        this.childTaskConsumer.stop();

        // Stop scheduled thread poll
        this.scheduledService.shutdown();

        // Stop master
        super.stop(type);
    }

    @Override
    public void destroyTaskContainer() {
        // Stop child task consumer
        this.childTaskConsumer.stop();

        // Stop scheduled thread poll
        this.scheduledService.shutdown();

        // Destroy task container
        super.destroyTaskContainer();
    }

    protected void reduce() {
        // Not find
        ProcessorHandler processorHandler = ProcessorUtil.getProcessor(this.jobInstanceDTO.getProcessorInfo());
        if (Objects.isNull(processorHandler) || Objects.isNull(processorHandler.getBaseProcessor())) {
            log.error("Not find processor! processorInfo={}", this.jobInstanceDTO.getProcessorInfo());
            return;
        }

        // Do reduce
        if (processorHandler.getBaseProcessor() instanceof MapReduceProcessor) {
            MapReduceProcessor mapReduceProcessor = (MapReduceProcessor) processorHandler.getBaseProcessor();
            JobContext jobContext = getReduceJobContext();
            ProcessResult processResult = new ProcessResult(false);
            try {
                ThreadLocalUtil.setJobContext(jobContext);
                processResult = mapReduceProcessor.reduce(jobContext);
            } catch (Throwable ex) {
                processResult.setResult(ex.toString());
            } finally {
                ThreadLocalUtil.removeJobContext();
            }

            this.persistReduceTask(processResult);
        }
    }

    protected JobContext getReduceJobContext() {
        JobContext jobContext = this.getBaseJobContext();
        jobContext.setTaskName(TaskConstant.MAP_TASK_REDUCE_NAME);
        jobContext.setTaskResultList(this.getReduceTaskResultList());
        return jobContext;
    }

    protected List<TaskResult> getReduceTaskResultList() {
        return null;
    }

    protected void persistReduceTask(ProcessResult processResult) {
        long jobId = this.jobInstanceDTO.getJobId();
        long instanceId = this.jobInstanceDTO.getJobInstanceId();
        long version = this.jobInstanceDTO.getDispatchVersion();
        long circleId = this.circleIdGenerator.get();

        Task task = new Task();
        task.setJobId(this.jobInstanceDTO.getJobId());
        task.setInstanceId(this.jobInstanceDTO.getJobInstanceId());
        task.setDispatchVersion(version);
        task.setCircleId(this.circleIdGenerator.get());
        String uniqueId = TaskUtil.getRandomUniqueId(jobId, instanceId, version, circleId, this.acquireTaskId());
        task.setTaskId(uniqueId);
        task.setTaskName(TaskConstant.MAP_TASK_REDUCE_NAME);
        task.setWorkerAddress(AddressUtil.getWorkerAddressByLocal(this.localWorkerAddress));

        // Second delay
        if (this.isSecondDelay()) {
            task.setTaskParentId(this.circleTaskUniqueId);
        } else {
            task.setTaskParentId(TaskConstant.DEFAULT_PARENT_ID);
        }

        task.setStatus(processResult.getStatus().getStatus());
        task.setResult(processResult.getResult());
        TaskDAO.INSTANCE.batchAdd(Collections.singletonList(task));
    }
}
