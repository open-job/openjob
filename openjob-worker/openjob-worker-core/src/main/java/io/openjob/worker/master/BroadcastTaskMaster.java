package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.worker.dto.JobInstanceDTO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class BroadcastTaskMaster extends AbstractTaskMaster {
    public BroadcastTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void submit() {

    }
}
