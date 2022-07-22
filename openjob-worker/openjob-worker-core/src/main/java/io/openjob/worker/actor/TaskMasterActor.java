package io.openjob.worker.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.ServerCheckTaskMasterRequest;
import io.openjob.common.request.ServerStopJobInstanceRequest;
import io.openjob.common.request.ServerSubmitJobInstanceRequest;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.master.TaskMaster;
import io.openjob.worker.master.TaskMasterFactory;
import io.openjob.worker.master.TaskMasterPool;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskMasterActor extends BaseActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServerSubmitJobInstanceRequest.class, this::submitJobInstance)
                .match(ServerStopJobInstanceRequest.class, this::stopJobInstance)
                .match(ServerCheckTaskMasterRequest.class, this::checkJobInstance)
                .build();
    }

    public void submitJobInstance(ServerSubmitJobInstanceRequest submitReq) {
        if (TaskMasterPool.contains(submitReq.getJobInstanceId())) {
            throw new RuntimeException(String.format("Task master is running! jobInstanceId=%s", submitReq.getJobInstanceId()));
        }

        JobInstanceDTO jobInstanceDTO = new JobInstanceDTO();
        TaskMaster taskMaster = TaskMasterPool.get(submitReq.getJobInstanceId(), (id) -> TaskMasterFactory.create(jobInstanceDTO, getContext()));
        taskMaster.submit();
    }

    public void stopJobInstance(ServerStopJobInstanceRequest stopReq) {
        if (!TaskMasterPool.contains(stopReq.getJobInstanceId())) {
            throw new RuntimeException(String.format("Task master is not running! jobInstanceId=%s", stopReq.getJobInstanceId()));
        }

        JobInstanceDTO jobInstanceDTO = new JobInstanceDTO();
        TaskMaster taskMaster = TaskMasterPool.get(stopReq.getJobInstanceId(), (id) -> TaskMasterFactory.create(jobInstanceDTO, getContext()));
        taskMaster.stop();
    }

    public void checkJobInstance(ServerCheckTaskMasterRequest checkTaskMasterRequest) {

    }
}
