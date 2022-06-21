package io.openjob.server.cluster.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class SlotsDTO {
    private Long serverId;
    private Set<Long> addSlots;
    private Set<Long> remoteSlots;
}
