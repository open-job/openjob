package io.openjob.worker.init;

import io.openjob.common.constant.ProtocolTypeEnum;
import io.openjob.common.request.WorkerStartRequest;
import io.openjob.common.response.ServerWorkerStartResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.OpenjobWorker;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class WorkerRegister {

    private final OpenjobWorker openjobWorker;

    /**
     * Initialize status
     */
    private final AtomicBoolean isInit = new AtomicBoolean(false);

    public WorkerRegister(OpenjobWorker openjobWorker) {
        this.openjobWorker = openjobWorker;
    }


    /**
     * Register
     */
    public void register() {
        // Already initialized
        if (this.isInit.get()) {
            return;
        }

        String serverAddress = WorkerConfig.getServerHost();

        WorkerStartRequest startReq = new WorkerStartRequest();
        startReq.setAddress(WorkerConfig.getWorkerAddress());
        startReq.setAppName(WorkerConfig.getAppName());
        startReq.setProtocolType(ProtocolTypeEnum.AKKA.getType());

        try {
            ServerWorkerStartResponse response = FutureUtil.mustAsk(WorkerUtil.getServerWorkerActor(), startReq, ServerWorkerStartResponse.class, 3000L);
            log.info("Register worker success. serverAddress={} workerAddress={}", serverAddress, WorkerConfig.getWorkerAddress());

            // Do register.
            this.doRegister(response);

            // Initialized
            this.isInit.set(true);
        } catch (Throwable e) {
            log.error("Register worker fail. serverAddress={} workerAddress={}", serverAddress, WorkerConfig.getWorkerAddress());
            throw e;
        }
    }

    /**
     * Do register.
     *
     * @param response response
     */
    private void doRegister(ServerWorkerStartResponse response) {
        this.openjobWorker.getWorkerContext().init(response.getAppId(), response.getWorkerAddressList());
    }
}
