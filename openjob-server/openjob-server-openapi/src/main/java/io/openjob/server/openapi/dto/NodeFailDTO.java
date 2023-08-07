package io.openjob.server.openapi.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class NodeFailDTO implements Serializable {

    /**
     * Cluster version.
     */
    private Long clusterVersion;

    /**
     * Fail server id.
     */
    private Long serverId;

    /**
     * Fail server ip.
     */
    private String ip;

    /**
     * Fail server akka address.
     */
    private String akkaAddress;
}
