package io.openjob.worker.init;

import io.openjob.common.constant.ProtocolTypeEnum;
import io.openjob.common.request.WorkerStartRequest;
import io.openjob.common.response.Result;
import io.openjob.common.util.FutureUtil;
import io.openjob.common.util.ResultUtil;
import io.openjob.worker.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class WorkerRegister {
    public WorkerRegister() {
    }

    public void register() throws Exception {
        String serverAddress = WorkerConfig.getServerHost();

        WorkerStartRequest startReq = new WorkerStartRequest();
        startReq.setAddress(WorkerConfig.getWorkerAddress());
        startReq.setAppName(WorkerConfig.getAppName());
        startReq.setProtocolType(ProtocolTypeEnum.AKKA.getType());

        try {
            Result<?> result = (Result<?>) FutureUtil.ask(WorkerUtil.getServerWorkerActor(), startReq, 3L);
            if (!ResultUtil.isSuccess(result)) {
                log.error("Register worker fail. serverAddress={} workerAddress={} message={}", serverAddress, WorkerConfig.getWorkerAddress(), result.getMessage());
                throw new RuntimeException(String.format("Register worker fail! message=%s", result.getMessage()));
            }

            log.info("Register worker success. serverAddress={} workerAddress={}", serverAddress, WorkerConfig.getWorkerAddress());
        } catch (Throwable e) {
            log.error("Register worker fail. serverAddress={} workerAddress={}", serverAddress, WorkerConfig.getWorkerAddress());
            throw e;
        }
    }
}
