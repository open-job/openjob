package io.openjob.worker.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.ServerDelayInstanceStopRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.WorkerResponse;
import io.openjob.worker.delay.DelayTaskManager;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayTaskMasterActor extends BaseActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServerDelayInstanceStopRequest.class, this::stopDelayInstance)
                .build();
    }

    public void stopDelayInstance(ServerDelayInstanceStopRequest request) {
        DelayTaskManager.INSTANCE.stopAndRemoveTaskInstance(request.getTaskId());
        getSender().tell(Result.success(new WorkerResponse()), getSelf());
    }
}
