package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.constant.TaskConstant;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.AddressUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class StandaloneTaskMaster extends AbstractTaskMaster {
    private ScheduledExecutorService secondDelayService;

    public StandaloneTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    protected void init() {
        super.init();

        // Second delay scheduler
        if (TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.secondDelayService = new ScheduledThreadPoolExecutor(
                    1,
                    new ThreadFactoryBuilder().setNameFormat("Openjob-heartbeat-thread").build(),
                    new ThreadPoolExecutor.AbortPolicy()
            );

            // Runnable
            this.secondDelayService.scheduleWithFixedDelay(() -> {
                try {
                    this.doTaskComplete();
                } catch (Throwable throwable) {
                    log.error("Standalone do complete failed!");
                }
            }, 5, 1, TimeUnit.SECONDS);
        }
    }

    @Override
    public void stop(Integer type) {
        super.stop(type);

        // Second delay to shut down scheduler
        if (TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            this.secondDelayService.shutdown();
            return;
        }

        // Do complete task.
        this.doCompleteTask();
    }

    @Override
    public void updateStatus(ContainerBatchTaskStatusRequest batchRequest) {
        // Update list
        List<Task> updateList = batchRequest.getTaskStatusList().stream().map(s -> {
            String taskUniqueId = s.getTaskUniqueId();
            return new Task(taskUniqueId, s.getStatus(), s.getResult());
        }).collect(Collectors.toList());

        // Update by status.
        updateList.stream().collect(Collectors.groupingBy(Task::getStatus))
                .forEach((status, groupList) -> taskDAO.batchUpdateStatusByTaskId(groupList, status));

        // Second delay scheduler
        // Async to do complete
        if (TimeExpressionTypeEnum.isSecondDelay(this.jobInstanceDTO.getTimeExpressionType())) {
            return;
        }

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
    public void submit() {
        // Switch running status.
        super.submit();

        // Create container
        MasterStartContainerRequest startRequest = this.getMasterStartContainerRequest();

        // Task
        Task task = this.convertToTask(startRequest, AddressUtil.getWorkerAddressByLocal(this.localWorkerAddress));
        task.setTaskParentId(TaskConstant.DEFAULT_PARENT_ID);
        taskDAO.add(task);

        // Add address.
        this.containerWorkers.add(AddressUtil.getWorkerAddressByLocal(this.localWorkerAddress));

        ActorSelection actorSelection = actorContext.actorSelection(this.localContainerPath);
        FutureUtil.mustAsk(actorSelection, startRequest, WorkerResponse.class, 3000L);

        // Add task manager
        this.addTask2Manager();
    }

    private void doTaskComplete() {
        // Not running
        if (!this.running.get()) {
            return;
        }

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
}
