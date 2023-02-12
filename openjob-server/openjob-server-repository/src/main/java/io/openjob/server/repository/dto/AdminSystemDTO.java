package io.openjob.server.repository.dto;

import lombok.Data;

/**
 * @author riki
 * @since 1.0.0
 */
@Data
public class AdminSystemDTO {
    /**
     * pk
     */
    private Integer id;

    /**
     * System version
     */
    private String version;

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
     * Delay zset slot.
     */
    private Integer delayZsetSlot;

    /**
     * Delay list slot.
     */
    private Integer delayAddListSlot;

    /**
     * Delay list slot.
     */
    private Integer delayStatusListSlot;

    /**
     * Delay list slot.
     */
    private Integer delayDeleteListSlot;

    /**
     * Max slot
     */
    private Integer maxSlot;

}
