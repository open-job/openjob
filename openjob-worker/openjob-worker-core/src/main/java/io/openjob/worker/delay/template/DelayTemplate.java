package io.openjob.worker.delay.template;

import io.openjob.common.request.WorkerDelayAddRequest;
import io.openjob.worker.exception.DelayException;
import io.openjob.worker.init.WorkerActorSystem;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DelayTemplate {

    /**
     * Send delay message
     *
     * @param message message
     * @return task id
     */
    public String send(DelayMessage message) {
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

        request.setTaskId(message.getTaskId());
        request.setTopic(message.getTopic());
        request.setParams(message.getParams());
        request.setExtra(message.getExtra());
        request.setExecuteTime(message.getExecuteTime());
        WorkerActorSystem.atLeastOnceDelivery(request, null);
        return message.getTaskId();
    }
}
