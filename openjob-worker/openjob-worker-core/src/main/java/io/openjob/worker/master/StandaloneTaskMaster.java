package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.request.MasterStartContainerRequest;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class StandaloneTaskMaster extends AbstractTaskMaster {
    public StandaloneTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void submit() {
        // Create container
        MasterStartContainerRequest startRequest = this.getMasterStartContainerRequest();
        taskDAO.add(this.convertToTask(startRequest));

        // Switch running status.
        if (!this.running.get()) {
            this.running.set(true);
        }

        ActorSelection actorSelection = actorContext.actorSelection(this.localContainerPath);
        FutureUtil.mustAsk(actorSelection, startRequest, WorkerResponse.class, 3000L);
    }
}
