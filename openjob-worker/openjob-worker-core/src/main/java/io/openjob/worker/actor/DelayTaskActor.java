package io.openjob.worker.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.ServerCheckDelayRequest;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskActor extends BaseActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServerCheckDelayRequest.class, this::handleCheckDelay)
                .build();
    }

    public void handleCheckDelay(ServerCheckDelayRequest checkDelayRequest) {

    }
}
