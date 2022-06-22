package io.openjob.server.cluster.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class SlotsDTO  implements Serializable {
    private Long serverId;
    private List<Long> addSlots;
    private List<Long> remoteSlots;
}
