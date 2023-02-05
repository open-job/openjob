package io.openjob.worker.init;

import io.openjob.common.request.WorkerStopRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.ServerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.common.util.ResultUtil;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class WorkerShutdown {
    private final OpenjobWorker openjobWorker;

    public WorkerShutdown(OpenjobWorker openjobWorker) {
        this.openjobWorker = openjobWorker;
    }

    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    /**
     * Openjob worker stop.
     *
     * @throws Exception exception
     */
    private void stop() throws Exception {
        String serverAddress = WorkerConfig.getServerHost();
        String workerAddress = WorkerConfig.getWorkerAddress();
        String appName = WorkerConfig.getAppName();

        WorkerStopRequest stopRequest = new WorkerStopRequest();
        stopRequest.setAppName(appName);
        stopRequest.setAddress(workerAddress);

        try {
            FutureUtil.mustAsk(WorkerUtil.getServerWorkerActor(), stopRequest, ServerResponse.class, 3000L);
        } catch (Throwable e) {
            log.error("Stop worker fail. serverAddress={} workerAddress={} message={}", serverAddress, workerAddress, e.getMessage());
        }
    }

    /**
     * Shutdown
     */
    private void shutdown() {
        // Stop worker heartbeat service.
        this.openjobWorker.getWorkerHeartbeat().shutdown();

        // Stop delay.
        this.openjobWorker.getDelayManager().stop();

        // Stop worker.
        try {
            this.stop();
        } catch (Throwable e) {
            log.error("Worker stop failed", e);
        }
    }
}
