package io.openjob.worker.master;

import akka.actor.ActorContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.TaskConstant;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.task.TaskQueue;
import io.openjob.common.util.TaskUtil;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.processor.MapReduceProcessor;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.processor.ProcessorHandler;
import io.openjob.worker.processor.TaskResult;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.request.ProcessorMapTaskRequest;
import io.openjob.worker.task.MapReduceTaskConsumer;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class MapReduceTaskMaster extends AbstractDistributeTaskMaster {

    /**
     * Child tasks.
     */
    protected TaskQueue<ProcessorMapTaskRequest> childTaskQueue;

    /**
     * Child task consumer
     */
    protected MapReduceTaskConsumer childTaskConsumer;

    /**
     * Task names map
     */
    protected Map<String, String> taskNamesMap = Maps.newConcurrentMap();


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
            childTaskQueue.submit(mapTaskReq);
        } catch (Throwable throwable) {
            log.error("Map reduce do map failed!", throwable);
            throw new RuntimeException(throwable);
        }
    }

    /**
     * Do map
     *
     * @param mapTaskReq mapTaskReq
     */
    public void doMap(ProcessorMapTaskRequest mapTaskReq) {
        // Add task name map
        this.taskNamesMap.put(mapTaskReq.getParentTaskName(), mapTaskReq.getTaskName());

        AtomicLong idListGenerator = new AtomicLong(mapTaskReq.getInitValueId());
        List<Long> mapTaskIds = mapTaskReq.getTasks().stream()
                .map(t -> idListGenerator.addAndGet(1L)).collect(Collectors.toList());
        String parentTaskId = TaskUtil.getRandomUniqueId(mapTaskReq.getJobId(), mapTaskReq.getJobInstanceId(),
                this.jobInstanceDTO.getDispatchVersion(), this.circleTaskId, mapTaskReq.getTaskId());

        // Last partition to delete redundant map task
        if (mapTaskReq.getTaskNum() > 0) {
            this.taskDAO.deleteRedundantMapTask(parentTaskId, Long.valueOf(mapTaskReq.getTaskNum()));
        }

        // Already map task ids by instanceId,parentTaskId
        List<Long> existMapTaskIds = this.taskDAO.getMapTaskList(parentTaskId, mapTaskIds);

        // Batch `MasterStartContainerRequest`
        AtomicLong idSetGenerator = new AtomicLong(mapTaskReq.getInitValueId());
        List<MasterStartContainerRequest> startRequestList = mapTaskReq.getTasks().stream().map(t -> {
            long mapTaskId = idSetGenerator.addAndGet(1L);
            MasterStartContainerRequest startReq = this.getMasterStartContainerRequest();
            startReq.setTask(t);
            startReq.setTaskName(mapTaskReq.getTaskName());
            startReq.setParentTaskId(mapTaskReq.getTaskId());
            startReq.setMapTaskId(mapTaskId);

            if (existMapTaskIds.contains(mapTaskId)) {
                startReq.setPersistent(CommonConstant.NO);
            }
            return startReq;
        }).collect(Collectors.toList());

        // Dispatch tasks
        this.dispatchTasks(startRequestList, false, Collections.emptySet());
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

            // Persist reduce task
            this.persistProcessResultTask(TaskConstant.MAP_TASK_REDUCE_NAME, processResult);
        }
    }

    protected JobContext getReduceJobContext() {
        JobContext jobContext = this.getBaseJobContext();
        jobContext.setTaskName(TaskConstant.MAP_TASK_REDUCE_NAME);
        jobContext.setTaskResultList(this.getReduceTaskResultList());
        return jobContext;
    }

    protected List<TaskResult> getReduceTaskResultList() {
        String reduceQueryTaskName = this.getReduceQueryTaskName();
        if (Objects.isNull(reduceQueryTaskName)) {
            return Lists.newArrayList();
        }

        return this.taskDAO.getListByTaskName(this.jobInstanceDTO.getJobInstanceId(), this.circleIdGenerator.get(), reduceQueryTaskName)
                .stream().map(this::convertTaskToTaskResult)
                .collect(Collectors.toList());
    }

    protected String getReduceQueryTaskName() {
        AtomicReference<String> taskName = new AtomicReference<>(null);
        Set<String> keys = this.taskNamesMap.keySet();
        this.taskNamesMap.values().forEach(n -> {
            if (!keys.contains(n)) {
                taskName.set(n);
            }
        });
        return taskName.get();
    }
}
