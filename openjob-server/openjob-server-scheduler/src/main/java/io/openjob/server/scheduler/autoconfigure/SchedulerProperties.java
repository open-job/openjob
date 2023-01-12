package io.openjob.server.scheduler.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "openjob.scheduler")
public class SchedulerProperties {
    private Integer instanceReDispatchPeriodTime = 90;
    private Integer instanceFailPeriodTime = 15;
    private Delay delay;

    @Data
    public static class Delay {
        private Boolean enable = false;
        private Integer zsetBathPopSize = 100;
    }
}
