package io.openjob.worker.actor;

import akka.actor.AbstractActor;
import io.openjob.common.request.ServerCheckTaskMasterRequest;
import io.openjob.common.request.ServerStopJobInstanceRequest;
import io.openjob.common.request.ServerSubmitJobInstanceRequest;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskMasterActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServerSubmitJobInstanceRequest.class, this::submitJobInstance)
                .match(ServerStopJobInstanceRequest.class, this::stopJobInstance)
                .match(ServerCheckTaskMasterRequest.class, this::checkJobInstance)
                .build();
    }

    public void submitJobInstance(ServerSubmitJobInstanceRequest submitJobInstanceRequest) {

    }

    public void stopJobInstance(ServerStopJobInstanceRequest stopJobInstanceRequest) {
        
    }

    public void checkJobInstance(ServerCheckTaskMasterRequest checkTaskMasterRequest) {

    }
}
