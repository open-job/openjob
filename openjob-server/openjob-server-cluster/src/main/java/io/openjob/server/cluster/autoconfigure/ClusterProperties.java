package io.openjob.server.cluster.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "openjob.cluster")
public class ClusterProperties {

    /**
     * Ping timeout(ms).
     */
    private Long pingTimeout = 3000L;

    /**
     * Ping node size.
     */
    private Integer pingSize = 5;

    /**
     * Node fail times.
     */
    private Integer nodeFailTimes = 3;

    /**
     * Cluster node period time(ms).
     */
    private Integer nodeFailPeriodTime = 15000;
}
