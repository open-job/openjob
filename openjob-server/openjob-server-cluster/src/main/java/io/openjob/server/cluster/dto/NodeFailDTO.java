package io.openjob.server.cluster.dto;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class NodeFailDTO {
    private Long serverId;
    private String ip;
    private String akkaAddress;
}
