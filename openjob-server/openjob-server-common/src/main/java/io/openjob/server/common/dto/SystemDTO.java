package io.openjob.server.common.dto;

import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class SystemDTO {

    /**
     * Cluster version
     */
    private Long clusterVersion;

    /**
     * Max slot
     */
    private Integer maxSlot;
}
