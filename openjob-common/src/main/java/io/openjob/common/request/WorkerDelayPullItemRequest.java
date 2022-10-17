package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerDelayPullItemRequest implements Serializable {
    private String topic;
    private Integer size;

    public WorkerDelayPullItemRequest(String topic, Integer size) {
        this.topic = topic;
        this.size = size;
    }
}
