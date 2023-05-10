package io.openjob.server.cluster.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class NodePingDTO implements Serializable {

    /**
     * Cluster version.
     */
    private Long clusterVersion;

    /**
     * Server id.
     */
    private Long serverId;
}
