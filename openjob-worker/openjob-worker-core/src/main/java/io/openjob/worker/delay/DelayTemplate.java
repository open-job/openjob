package io.openjob.worker.delay;

import io.openjob.common.request.WorkerDelayAddRequest;
import io.openjob.common.response.ServerDelayAddResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.config.OpenjobConfig;
import io.openjob.worker.constant.WorkerConstant;
import io.openjob.worker.exception.DelayException;
import io.openjob.worker.util.WorkerUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Slf4j
public class DelayTemplate {

    /**
     * Delay timeout(ms)
     */
    private Long timeout;

    public DelayTemplate() {
        this.timeout = OpenjobConfig.getLong(WorkerConstant.WORKER_DELAY_TIMEOUT, WorkerConstant.DEFAULT_WORKER_DELAY_TIMEOUT);
    }

    /**
     * @param timeout timeout
     */
    public DelayTemplate(Long timeout) {
        this.timeout = timeout;
    }

    /**
     * Send delay message
     *
     * @param message message
     * @return task id
     */
    public String send(DelayMessage message) {
        return this.send(message, this.timeout);
    }

    /**
     * Send delay message
     *
     * @param message message
     * @return task id
     */
    public String send(DelayMessage message, Long timeout) {
        WorkerDelayAddRequest request = new WorkerDelayAddRequest();

        // Default unique id.
        if (StringUtils.isEmpty(message.getTaskId())) {
            message.setTaskId(UUID.randomUUID().toString());
        }

        // Topic
        if (StringUtils.isEmpty(message.getTopic())) {
            throw new DelayException("Delay topic can not be empty!");
        }

        // Execute time
        if (Objects.isNull(message.getExecuteTime())) {
            throw new DelayException("Delay execute time can not be null!");
        }

        // Delay message
        request.setTaskId(message.getTaskId());
        request.setTopic(message.getTopic());
        request.setParams(message.getParams());
        request.setExtra(message.getExtra());
        request.setExecuteTime(message.getExecuteTime());

        ServerDelayAddResponse response = FutureUtil.mustAsk(WorkerUtil.getServerDelayInstanceActor(), request, ServerDelayAddResponse.class, timeout);
        log.info("Delay task send success! taskId={}", response.getTaskId());
        return message.getTaskId();
    }
}
