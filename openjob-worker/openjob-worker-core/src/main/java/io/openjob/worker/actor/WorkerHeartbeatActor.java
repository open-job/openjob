package io.openjob.worker.actor;

import akka.actor.AbstractActor;
import io.openjob.common.request.WorkerHeartbeatReportRequest;
import io.openjob.common.response.Result;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerHeartbeatActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerHeartbeatReportRequest.class, this::WorkerHeartbeatCheckRequest)
                .build();
    }

    public void WorkerHeartbeatCheckRequest(WorkerHeartbeatReportRequest workerHeartbeatCheckRequest) {
        getSender().tell(Result.success(new Object()), getSelf());
    }
}
