package io.openjob.worker.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.response.Result;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.KryoUtil;
import io.openjob.worker.container.TaskContainer;
import io.openjob.worker.container.TaskContainerFactory;
import io.openjob.worker.container.TaskContainerPool;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.request.MasterBatchStartContainerRequest;
import io.openjob.worker.request.MasterDestroyContainerRequest;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.request.MasterStopContainerRequest;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TaskContainerActor extends BaseActor {
    private static final ThreadPoolExecutor CONTAINER_EXECUTOR;

    static {
        CONTAINER_EXECUTOR = new ThreadPoolExecutor(
                2,
                2,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                r -> new Thread(r, "Openjob-container-executor"),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        CONTAINER_EXECUTOR.allowCoreThreadTimeOut(true);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MasterStartContainerRequest.class, this::handleStartContainer)
                .match(MasterBatchStartContainerRequest.class, this::handleBatchStartContainer)
                .match(MasterStopContainerRequest.class, this::handleStopContainer)
                .match(MasterDestroyContainerRequest.class, this::handleDestroyContainer)
                .build();
    }

    public void handleStartContainer(MasterStartContainerRequest startReq) {
        this.startContainer(startReq);
        getSender().tell(Result.success(new WorkerResponse()), getSelf());
    }

    public void handleBatchStartContainer(MasterBatchStartContainerRequest batchStartReq) {
        CONTAINER_EXECUTOR.submit(new ContainerRunnable(batchStartReq));
        getSender().tell(Result.success(new WorkerResponse()), getSelf());
    }

    public void handleStopContainer(MasterStopContainerRequest stopReq) {

    }

    public void handleDestroyContainer(MasterDestroyContainerRequest destroyReq) {

    }

    private void startContainer(MasterStartContainerRequest startReq) {
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
        jobContext.setTaskName(startReq.getTaskName());
        jobContext.setMasterActorPath(startReq.getMasterAkkaPath());
        if (Objects.nonNull(startReq.getTask())) {
            jobContext.setTask(KryoUtil.deserialize(startReq.getTask()));
        }

        TaskContainer taskContainer = TaskContainerPool.get(startReq.getJobInstanceId(), id -> TaskContainerFactory.create(startReq));
        taskContainer.execute(jobContext);
    }

    private class ContainerRunnable implements Runnable {
        private final MasterBatchStartContainerRequest containerRequest;

        public ContainerRunnable(MasterBatchStartContainerRequest containerRequest) {
            this.containerRequest = containerRequest;
        }

        @Override
        public void run() {
            try {
                for (MasterStartContainerRequest req : this.containerRequest.getStartContainerRequests()) {
                    startContainer(req);
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }
}
