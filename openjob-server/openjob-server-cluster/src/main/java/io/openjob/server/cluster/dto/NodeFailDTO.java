package io.openjob.server.cluster.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class NodeFailDTO implements Serializable {
    private Long serverId;
    private String ip;
    private String akkaAddress;
    private List<SlotsDTO> slotsDTOS;
}
