package io.openjob.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class WorkerDelayItemPullRequest implements Serializable {
    private String topic;
    private Integer size;

    public WorkerDelayItemPullRequest() {
    }

    public WorkerDelayItemPullRequest(String topic, Integer size) {
        this.topic = topic;
        this.size = size;
    }
}
