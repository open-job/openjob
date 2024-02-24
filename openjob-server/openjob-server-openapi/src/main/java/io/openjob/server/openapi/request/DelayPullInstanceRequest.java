package io.openjob.server.openapi.request;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class DelayPullInstanceRequest {
    private String workerAddress;
    private List<DelayPullTopicItemRequest> list;

    @Data
    public static class DelayPullTopicItemRequest {
        private String topic;
        private Integer size;
    }
}
