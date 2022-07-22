package io.openjob.worker.actor;

import akka.actor.AbstractActor;
import io.openjob.common.request.ServerWorkerHeartbeatRequest;
import io.openjob.common.response.Result;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerHeartbeatActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServerWorkerHeartbeatRequest.class, this::WorkerHeartbeatCheckRequest)
                .build();
    }

    public void WorkerHeartbeatCheckRequest(ServerWorkerHeartbeatRequest workerHeartbeatCheckRequest) {
        getSender().tell(Result.success(new Object()), getSelf());
    }
}
