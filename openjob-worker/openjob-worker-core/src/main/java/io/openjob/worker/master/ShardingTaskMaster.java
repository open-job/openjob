package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.worker.dto.JobInstanceDTO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ShardingTaskMaster extends DistributeTaskMaster {
    public ShardingTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void submit() {
    }
}
