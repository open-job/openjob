package io.openjob.worker.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.WorkerStartContainerRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.WorkerResponse;
import io.openjob.worker.container.TaskContainer;
import io.openjob.worker.container.TaskContainerFactory;
import io.openjob.worker.context.JobContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskContainerActor extends BaseActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerStartContainerRequest.class, this::handleStartContainer)
                .build();
    }

    public void handleStartContainer(WorkerStartContainerRequest startReq) {
        this.startContainer(startReq);
        getSender().tell(Result.success(new WorkerResponse()), getSelf());
    }

    private void startContainer(WorkerStartContainerRequest startReq) {
        JobContext jobContext = new JobContext();
        jobContext.setJobId(startReq.getJobId());
        jobContext.setJobInstanceId(startReq.getJobInstanceId());
        jobContext.setTaskId(startReq.getTaskId());
        jobContext.setJobParams(startReq.getJobParams());
        jobContext.setProcessorType(startReq.getProcessorType());
        jobContext.setProcessorInfo(startReq.getProcessorInfo());
        jobContext.setFailRetryInterval(startReq.getFailRetryInterval());
        jobContext.setFailRetryTimes(startReq.getFailRetryTimes());
        jobContext.setExecuteType(startReq.getExecuteType());
        jobContext.setConcurrency(startReq.getConcurrency());
        jobContext.setTimeExpression(startReq.getTimeExpression());
        jobContext.setTimeExpressionType(startReq.getTimeExpressionType());
        jobContext.setWorkerAddresses(startReq.getWorkerAddresses());

        TaskContainer taskContainer = TaskContainerFactory.create(jobContext);
        TaskContainerFactory.getPool().submit(
                jobContext.getJobId(),
                jobContext.getJobInstanceId(),
                jobContext.getTaskId(),
                jobContext.getConcurrency(),
                taskContainer
        );
        getSender().tell(Result.success(new WorkerResponse()), getSelf());
    }
}
