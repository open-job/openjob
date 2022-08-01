package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.dto.JobInstanceDTO;

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
        ActorSelection actorSelection = actorContext.actorSelection(this.localContainerPath);
        FutureUtil.mustAsk(actorSelection, this.wrapMasterStartContainerRequest(), WorkerResponse.class, 3L);
    }

    @Override
    public void stop() {

    }
}
