package io.openjob.worker.init;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.response.ServerHeartbeatResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.master.TaskMasterPool;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class WorkerHeartbeat {

    private final OpenjobWorker openjobWorker;

    /**
     * Worker heartbeat
     */
    private final ScheduledExecutorService heartbeatService;

    public WorkerHeartbeat(OpenjobWorker openjobWorker) {
        this.openjobWorker = openjobWorker;
        this.heartbeatService = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryBuilder().setNameFormat("Openjob-heartbeat-thread").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public void init() {
        int heartbeatInterval = OpenjobConfig.getInteger(WorkerConstant.WORKER_HEARTBEAT_INTERVAL, WorkerConstant.DEFAULT_WORKER_HEARTBEAT_INTERVAL);
        heartbeatService.scheduleAtFixedRate(() -> {
            String workerAddress = WorkerConfig.getWorkerAddress();
            String serverAddress = WorkerConfig.getServerHost();

            WorkerHeartbeatRequest heartbeatReq = new WorkerHeartbeatRequest();
            heartbeatReq.setAppId(WorkerContext.getAppId());
            heartbeatReq.setAddress(workerAddress);
            heartbeatReq.setAppName(OpenjobConfig.getString(WorkerConstant.WORKER_APP_NAME));
            heartbeatReq.setVersion("1.0");
            heartbeatReq.setRunningJobInstanceIds(TaskMasterPool.getRunningTask());
            try {
                ServerHeartbeatResponse heartbeatResponse = FutureUtil.mustAsk(WorkerUtil.getServerHeartbeatActor(), heartbeatReq, ServerHeartbeatResponse.class, 3000L);
                log.info("Worker heartbeat success. serverAddress={} workerAddress={}", serverAddress, workerAddress);

                // Refresh worker.
                this.refresh(heartbeatResponse);
            } catch (Throwable e) {
                log.error("Worker heartbeat fail. serverAddress={} workerAddress={}", serverAddress, workerAddress, e);
            }

        }, 5, heartbeatInterval, TimeUnit.SECONDS);
    }

    /**
     * Stop heartbeat.
     */
    public void shutdown() {
        // Stop worker heartbeat service.
        heartbeatService.shutdownNow();
    }

    /**
     * Refresh worker.
     */
    private void refresh(ServerHeartbeatResponse heartbeatResponse) {
        // Refresh online workers.
        this.refreshOnlineWorkers(heartbeatResponse);

        // Refresh delay.
        if (WorkerConfig.getDelayEnable()) {
            this.openjobWorker.getDelayManager().refresh(heartbeatResponse.getSystemResponse());
        }
    }

    /**
     * Refresh online workers.
     *
     * @param heartbeatResponse heartbeatResponse
     */
    private void refreshOnlineWorkers(ServerHeartbeatResponse heartbeatResponse) {
        Set<String> offlineWorkers = this.openjobWorker.getWorkerContext().refreshOnlineWorkers(heartbeatResponse.getWorkerAddressList());
        if (!CollectionUtils.isEmpty(offlineWorkers)) {
            TaskMasterPool.offlineWorkers(offlineWorkers);
        }
    }
}
