package io.openjob.worker.container;

import io.openjob.worker.context.JobContext;
import io.openjob.worker.request.MasterStartContainerRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ThreadTaskContainer extends BaseTaskContainer {

    protected ExecutorService executorService;

    protected ScheduledExecutorService scheduledService;

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

    }
}
