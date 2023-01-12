package io.openjob.worker.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.ServerCheckTaskMasterRequest;
import io.openjob.common.request.ServerStopJobInstanceRequest;
import io.openjob.common.request.ServerSubmitJobInstanceRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.WorkerResponse;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.master.MapReduceTaskMaster;
import io.openjob.worker.master.TaskMaster;
import io.openjob.worker.master.TaskMasterFactory;
import io.openjob.worker.master.TaskMasterPool;
import io.openjob.worker.request.ContainerBatchTaskStatusRequest;
import io.openjob.worker.request.ProcessorMapTaskRequest;

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
                .match(ContainerBatchTaskStatusRequest.class, this::handleContainerTaskStatus)
                .match(ProcessorMapTaskRequest.class, this::handleProcessorMapTask)
                .build();
    }

    /**
     * Submit job instance.
     *
     * @param submitReq submit request.
     */
    public void submitJobInstance(ServerSubmitJobInstanceRequest submitReq) {
        if (TaskMasterPool.contains(submitReq.getJobInstanceId())) {
            throw new RuntimeException(String.format("Task master is running! jobInstanceId=%s", submitReq.getJobInstanceId()));
        }

        getSender().tell(Result.success(new WorkerResponse()), getSelf());

        JobInstanceDTO jobInstanceDTO = new JobInstanceDTO();
        jobInstanceDTO.setJobId(submitReq.getJobId());
        jobInstanceDTO.setJobInstanceId(submitReq.getJobInstanceId());
        jobInstanceDTO.setJobParams(submitReq.getJobParams());
        jobInstanceDTO.setWorkflowId(submitReq.getWorkflowId());
        jobInstanceDTO.setExecuteType(submitReq.getExecuteType());
        jobInstanceDTO.setProcessorType(submitReq.getProcessorType());
        jobInstanceDTO.setProcessorInfo(submitReq.getProcessorInfo());
        jobInstanceDTO.setFailRetryInterval(submitReq.getFailRetryInterval());
        jobInstanceDTO.setFailRetryTimes(submitReq.getFailRetryTimes());
        jobInstanceDTO.setConcurrency(submitReq.getConcurrency());
        jobInstanceDTO.setTimeExpression(submitReq.getTimeExpression());
        jobInstanceDTO.setTimeExpressionType(submitReq.getTimeExpressionType());

        TaskMaster taskMaster = TaskMasterPool.get(submitReq.getJobInstanceId(), (id) -> TaskMasterFactory.create(jobInstanceDTO, getContext()));
        taskMaster.submit();
    }

    /**
     * Stop job instance.
     *
     * @param stopReq stop request.
     */
    public void stopJobInstance(ServerStopJobInstanceRequest stopReq) {
        if (!TaskMasterPool.contains(stopReq.getJobInstanceId())) {
            throw new RuntimeException(String.format("Task master is not running! jobInstanceId=%s", stopReq.getJobInstanceId()));
        }

        JobInstanceDTO jobInstanceDTO = new JobInstanceDTO();
        TaskMaster taskMaster = TaskMasterPool.get(stopReq.getJobInstanceId(), (id) -> TaskMasterFactory.create(jobInstanceDTO, getContext()));
        taskMaster.stop();
    }

    /**
     * Check job instance.
     *
     * @param checkRequest check request.
     */
    public void checkJobInstance(ServerCheckTaskMasterRequest checkRequest) {
        if (TaskMasterPool.contains(checkRequest.getJobInstanceId())) {
            getSender().tell(Result.success(new WorkerResponse()), getSelf());
            return;
        }

        getSender().tell(Result.fail("Task master is not exist! instanceId=" + checkRequest.getJobInstanceId()), getSelf());
    }

    /**
     * Hande container task status.
     *
     * @param batchTaskStatusReq status request.
     */
    public void handleContainerTaskStatus(ContainerBatchTaskStatusRequest batchTaskStatusReq) {
        TaskMaster taskMaster = TaskMasterPool.get(batchTaskStatusReq.getJobInstanceId());
        taskMaster.updateStatus(batchTaskStatusReq);

        WorkerResponse workerResponse = new WorkerResponse(batchTaskStatusReq.getDeliveryId());
        getSender().tell(Result.success(workerResponse), getSelf());
    }

    /**
     * Handle map task.
     *
     * @param mapTaskReq map task request.
     */
    public void handleProcessorMapTask(ProcessorMapTaskRequest mapTaskReq) {
        TaskMaster taskMaster = TaskMasterPool.get(mapTaskReq.getJobInstanceId());
        if (taskMaster instanceof MapReduceTaskMaster) {
            ((MapReduceTaskMaster) taskMaster).map(mapTaskReq);
        }

        getSender().tell(Result.success(new WorkerResponse()), getSelf());
    }
}
