package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import io.openjob.common.request.WorkerManagerRequest;
import io.openjob.common.response.Result;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.util.WorkerUtil;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class StandaloneTaskMaster extends BaseTaskMaster {
    public StandaloneTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void submit() {
        WorkerManagerRequest workerManagerRequest = new WorkerManagerRequest();
        ActorSelection actorSelection = actorContext.actorSelection(this.localManagerPath);

        try {
            FutureUtil.ask(actorSelection, workerManagerRequest, 3L);
        } catch (Throwable ex) {
            throw new RuntimeException("");
        }
    }

    @Override
    public void stop() {

    }
}
