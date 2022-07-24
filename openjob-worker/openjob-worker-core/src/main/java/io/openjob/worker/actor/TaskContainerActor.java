package io.openjob.worker.actor;

import io.openjob.common.actor.BaseActor;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskContainerActor extends BaseActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }
}
