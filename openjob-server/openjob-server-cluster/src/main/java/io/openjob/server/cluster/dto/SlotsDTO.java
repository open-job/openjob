package io.openjob.server.cluster.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class SlotsDTO {
    private Long serverId;
    private List<Long> addSlots;
    private List<Long> remoteSlots;
}
