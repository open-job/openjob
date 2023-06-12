package io.openjob.server.cluster.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "openjob.cluster")
public class ClusterProperties {

    /**
     * Cluster worker
     */
    private ClusterProperties.Worker worker = new ClusterProperties.Worker();

    /**
     * Ping timeout(ms).
     */
    private Long pingTimeout = 3000L;

    /**
     * Spread node size.
     */
    private Integer spreadSize = 3;

    /**
     * Spread retry times.
     */
    private Integer spreadRetryTimes = 3;

    /**
     * Node fail times.
     */
    private Integer nodeFailTimes = 3;

    /**
     * Node success times.
     */
    private Integer nodeSuccessTimes = 3;

    /**
     * Cluster node fail period time(ms).
     */
    private Integer nodeFailPeriodTime = 15000;

    /**
     * Cluster node success period time(ms).
     */
    private Integer nodeSuccessPeriodTime = 15000;

    @Data
    public static class Worker {

        /**
         * Worker online period(s).
         */
        private Integer onlinePeriod = 10;

        /**
         * Worker offline period(s).
         */
        private Integer offlinePeriod = 20;
    }
}
