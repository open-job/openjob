package io.openjob.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ServerHeartbeatSystemResponse implements Serializable {

    /**
     * Cluster version
     */
    private Long clusterVersion;

    /**
     * Cluster delay version
     */
    private Long clusterDelayVersion;
}
