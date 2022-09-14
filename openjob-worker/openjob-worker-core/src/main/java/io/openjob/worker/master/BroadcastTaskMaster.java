package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.JobProcessor;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.WorkerUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class BroadcastTaskMaster extends DistributeTaskMaster {
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
    public void submit() {
        // Preprocess
        try {
            JobContext jobContext = this.getBaseJobContext();
            jobContext.setTaskName(WorkerConstant.BROADCAST_NAME);
            BaseProcessor process = ProcessorUtil.getProcess(this.jobInstanceDTO.getProcessorInfo());
            if (process instanceof JobProcessor) {
                ((JobProcessor) process).preProcess(jobContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> workerAddresses = this.jobInstanceDTO.getWorkerAddresses();
        workerAddresses.forEach(workerAddress -> {
            String workerPath = WorkerUtil.getWorkerContainerActorPath(workerAddress);
            ActorSelection workerSelection = actorContext.actorSelection(workerPath);
            MasterStartContainerRequest startRequest = this.getMasterStartContainerRequest();

            // Persist tasks.
            this.persistTasks(Collections.singletonList(startRequest));

            try {
                FutureUtil.ask(workerSelection, startRequest, 10L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void postProcess() {

    }
}
