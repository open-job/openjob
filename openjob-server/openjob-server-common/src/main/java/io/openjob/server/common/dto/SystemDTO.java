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
     * Cluster delay version
     */
    private Long clusterDelayVersion;

    /**
     * Cluster supervisor slot.
     */
    private Integer clusterSupervisorSlot;

    /**
     * Worker supervisor slot.
     */
    private Integer workerSupervisorSlot;

    /**
     * Max slot
     */
    private Integer maxSlot;

    /**
     * Delay task max slot.
     */
    private Integer delayZsetMaxSlot;

    /**
     * Delay list max slot.
     */
    private Integer delayListMaxSlot;
}
