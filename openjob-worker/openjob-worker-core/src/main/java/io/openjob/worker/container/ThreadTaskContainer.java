package io.openjob.worker.container;

import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.request.MasterStartContainerRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class ThreadTaskContainer extends BaseTaskContainer {

    protected ExecutorService executorService;

    /**
     * New thread task container.
     *
     * @param startRequest start request.
     */
    public ThreadTaskContainer(MasterStartContainerRequest startRequest) {
        super(startRequest);

        executorService = new ThreadPoolExecutor(
                this.startRequest.getConcurrency(),
                this.startRequest.getConcurrency(),
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                r -> new Thread(r, "Openjob-container-thread")
        );
    }

    @Override
    public void execute(JobContext jobContext) {
        this.executorService.submit(new ThreadTaskProcessor(jobContext));
    }

    @Override
    public void stop() {
        // stop
        this.executorService.shutdownNow();

        // report status.
        this.reportStopStatus();

        // remove from pool
        TaskContainerPool.remove(startRequest.getJobInstanceId());
    }

    @Override
    public void destroy() {
        // stop
        this.executorService.shutdownNow();

        // remove from pool
        TaskContainerPool.remove(startRequest.getJobInstanceId());
    }

    private void reportStopStatus() {
        ContainerTaskStatusRequest request = new ContainerTaskStatusRequest();
        request.setJobId(startRequest.getJobId());
        request.setJobInstanceId(startRequest.getJobInstanceId());
        request.setTaskId(startRequest.getTaskId());
        request.setWorkerAddress("");
        request.setMasterActorPath(startRequest.getMasterAkkaPath());
        request.setStatus(TaskStatusEnum.FAILED.getStatus());
        request.setResult("stopped");

        TaskStatusReporter.report(request);
    }
}
