package io.openjob.server.openapi.vo;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class DelayPullTopicVO {
    private List<DelayTopicVO> topicList;

    @Data
    public static class DelayTopicVO {
        private Long id;
        private Long pid;
        private String topic;
        private String processorInfo;
        private Integer failRetryTimes;
        private Integer failRetryInterval;
        private Integer executeTimeout;
        private Integer concurrency;
        private Integer failTopicEnable;
        private Integer failTopicConcurrency;
    }
}
