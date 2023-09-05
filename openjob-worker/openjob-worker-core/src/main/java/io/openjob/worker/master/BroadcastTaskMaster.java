package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.init.WorkerContext;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class BroadcastTaskMaster extends AbstractDistributeTaskMaster {
    public BroadcastTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void completeTask() throws InterruptedException {
        // Post process
        this.postProcess();

        // complete task
        super.completeTask();
    }

    @Override
    public void doSubmit() {
        // Second delay to persist circle task
        if (this.isSecondDelay()) {
            this.persistCircleTask();
        }

        // Dispatch tasks
        AtomicLong index = new AtomicLong(1L);
        WorkerContext.getOnlineWorkers().forEach(workerAddress -> {
            ActorSelection workerSelection = WorkerUtil.getWorkerContainerActor(workerAddress);
            MasterStartContainerRequest startRequest = this.getMasterStartContainerRequest();
            startRequest.setTaskName(TaskUtil.getBroadcastTaskName(index.get()));

            try {
                // Dispatch task
                FutureUtil.mustAsk(workerSelection, startRequest, WorkerResponse.class, 3000L);

                // Dispatch success to persist task.
                this.persistTasks(workerAddress, Collections.singletonList(startRequest));
                index.getAndIncrement();
            } catch (Exception e) {
                log.warn(String.format("Broadcast failed! workerAddress=%s", workerAddress));
            }
        });

        // Add task manager
        this.addTask2Manager();
    }

    protected void postProcess() {

    }

    @Override
    public void stop(Integer type) {
        // Stop scheduled thread poll
        this.scheduledService.shutdown();

        // Stop master
        super.stop(type);
    }

    @Override
    public void destroyTaskContainer() {
        // Stop scheduled thread poll
        this.scheduledService.shutdown();

        // Destroy task container
        super.destroyTaskContainer();
    }
}
