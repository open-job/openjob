package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerResponse implements Serializable {
    private Long deliveryId;

    /**
     * Non arg constructor for Serializable.
     */
    @SuppressWarnings("unused")
    public WorkerResponse() {
    }

    public WorkerResponse(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
}
