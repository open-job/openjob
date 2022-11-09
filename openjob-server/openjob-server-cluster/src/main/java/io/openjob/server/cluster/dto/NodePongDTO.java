package io.openjob.server.cluster.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class NodePongDTO implements Serializable {

    /**
     * Cluster version.
     */
    private Long clusterVersion;

    /**
     * Response server id.
     */
    private Long serverId;

    /**
     * Whether ping server is know.
     */
    private Boolean knowServer;
}
