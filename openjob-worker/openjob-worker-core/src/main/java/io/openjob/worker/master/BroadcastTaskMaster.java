package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.init.WorkerContext;
import io.openjob.worker.processor.ProcessorHandler;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.ProcessorUtil;
import io.openjob.worker.util.WorkerUtil;

import java.util.Collections;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
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
    public void submit() {
        // Preprocess
        try {
            JobContext jobContext = this.getBaseJobContext();
            jobContext.setTaskName(WorkerConstant.BROADCAST_NAME);
            ProcessorHandler processorHandler = ProcessorUtil.getProcessor(this.jobInstanceDTO.getProcessorInfo());
            assert processorHandler != null;
            processorHandler.preProcess(jobContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WorkerContext.getOnlineWorkers().forEach(workerAddress -> {
            ActorSelection workerSelection = WorkerUtil.getWorkerContainerActor(workerAddress);
            MasterStartContainerRequest startRequest = this.getMasterStartContainerRequest();

            // Persist tasks.
            this.persistTasks(workerAddress, Collections.singletonList(startRequest));

            try {
                FutureUtil.mustAsk(workerSelection, startRequest, WorkerResponse.class, 3000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void postProcess() {

    }
}
