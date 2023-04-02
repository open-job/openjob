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
     * Worker supervisor slot.
     */
    private Integer workerSupervisorSlot;

    /**
     * Max slot
     */
    private Integer maxSlot;

    /**
     * Delay set slot
     */
    private Integer delayZsetMaxSlot;

    /**
     * Delay fail set slot
     */
    private Integer delayFailZsetMaxSlot;

    /**
     * Delay add list slot
     */
    private Integer delayAddListMaxSlot;

    /**
     * Delay status slot
     */
    private Integer delayStatusListMaxSlot;

    /**
     * Delay delete list slot
     */
    private Integer delayDeleteListMaxSlot;

    /**
     * Job keep days
     */
    private Integer jobKeepDays;

    /**
     * Delay keep days
     */
    private Integer delayKeepDays;

    /**
     * Server keep days
     */
    private Integer serverKeepDays;

    /**
     * Worker keep days
     */
    private Integer workerKeepDays;
}
