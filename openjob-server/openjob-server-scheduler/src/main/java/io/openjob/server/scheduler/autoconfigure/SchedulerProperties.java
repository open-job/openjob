package io.openjob.server.scheduler.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "openjob.scheduler")
public class SchedulerProperties {
    private Integer instanceReDispatchPeriodTime = 90;
    private Integer instanceFailPeriodTime = 15;
    private SchedulerProperties.Scheduler scheduler = new SchedulerProperties.Scheduler();
    private SchedulerProperties.Workflow workflow = new SchedulerProperties.Workflow();
    private Delay delay = new Delay();

    @Data
    public static class Scheduler {
        private Integer timingWheelSize = 1;
        private Integer executorMaxPoolSize = 16;
        private Integer executorBlockingSize = 4;

        /**
         * Executor keep alive time(Second)
         */
        private Integer executorKeepAliveTime = 90;
    }

    @Data
    public static class Workflow {
        private Integer timingWheelSize = 1;
        private Integer executorMaxPoolSize = 8;
        private Integer executorBlockingSize = 4;

        /**
         * Executor keep alive time(Second)
         */
        private Integer executorKeepAliveTime = 90;
    }

    @Data
    public static class Delay {
        private Boolean enable = false;
        private Integer zsetBathPopSize = 100;
    }
}
