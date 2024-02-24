package io.openjob.server.openapi.vo;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Data
public class DelayPullInstanceVO {
    private List<DelayPullInstanceItemVO> list;

    @Data
    public static class DelayPullInstanceItemVO {
        private String topic;
        private String taskId;
        private Long delayId;
        private Long delayPid;
        private String delayParams;
        private String delayExtra;
        private String processorInfo;
        private Integer failRetryTimes;
        private Integer failRetryInterval;
        private Integer executeTimeout;
        private Integer blockingSize;
        private Integer concurrency;
        private Integer failTopicEnable;
        private Integer failTopicConcurrency;
    }
}
