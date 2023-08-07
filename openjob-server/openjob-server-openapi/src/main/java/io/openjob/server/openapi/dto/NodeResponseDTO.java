package io.openjob.server.openapi.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class NodeResponseDTO implements Serializable {

    /**
     * Join server id.
     */
    private Long serverId;

    /**
     * Join server ip.
     */
    private String ip;

    /**
     * Join server akka address
     */
    private String akkaAddress;
}
