package io.openjob.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerDelayAddResponse extends ServerResponse {

    /**
     * Delay task unique id.
     * If is null or blank, will to auto generate.
     */
    private String taskId;

    public ServerDelayAddResponse(Long deliveryId) {
        super(deliveryId);
    }
}
