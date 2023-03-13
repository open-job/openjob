package io.openjob.worker.container;

import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.context.JobContext;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.request.ContainerTaskStatusRequest;
import io.openjob.worker.request.MasterStartContainerRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ThreadTaskContainer extends BaseTaskContainer {

    protected ExecutorService executorService;

    protected AtomicLong processorId = new AtomicLong(1);

    protected Map<Long, TaskProcessor> processorMap = new HashMap<>();

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
        Long id = processorId.getAndIncrement();
        ThreadTaskProcessor threadTaskProcessor = new ThreadTaskProcessor(id, jobContext, i -> this.processorMap.remove(i));
        this.processorMap.put(id, threadTaskProcessor);

        this.executorService.submit(threadTaskProcessor);
    }

    @Override
    public void stop() {
        // stop
        this.executorService.shutdownNow();

        // Stop processor.
        this.processorMap.forEach((i, p) -> {
            p.stop();
        });
        this.processorMap.clear();

        // report status.
        this.reportStopStatus();

        // remove from pool
        TaskContainerPool.remove(startRequest.getJobInstanceId());
    }

    @Override
    public void destroy() {
        // stop
        this.executorService.shutdownNow();

        // Clear
        this.processorMap.clear();

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
        request.setResult("stop");

        TaskStatusReporter.report(request);
    }
}
