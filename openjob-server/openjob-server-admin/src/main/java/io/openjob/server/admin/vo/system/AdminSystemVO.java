package io.openjob.server.admin.vo.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminSystemVO", description = "Admin System query VO")
public class AdminSystemVO {


    @ApiModelProperty(value = "PK")
    private Long id;

    /**
     * System version
     */
    @ApiModelProperty(value = "version")
    private String version;

    /**
     * Cluster version
     */
    @ApiModelProperty(value = "cluster_version")
    private Long clusterVersion;

    /**
     * Cluster delay version
     */
    @ApiModelProperty(value = "cluster_delay_version")
    private Long clusterDelayVersion;

    /**
     * Cluster supervisor slot.
     */
    @ApiModelProperty(value = "cluster_supervisor_slot")
    private Integer clusterSupervisorSlot;

    /**
     * Worker supervisor slot.
     */
    @ApiModelProperty(value = "worker_supervisor_slot")
    private Integer workerSupervisorSlot;

    /**
     * Delay zset slot.
     */
    @ApiModelProperty(value = "delay_zset_slot")
    private Integer delayZsetSlot;

    /**
     * Delay list slot.
     */
    @ApiModelProperty(value = "delay_add_list_slot")
    private Integer delayAddListSlot;

    /**
     * Delay list slot.
     */
    @ApiModelProperty(value = "delay_status_list_slot")
    private Integer delayStatusListSlot;

    /**
     * Delay list slot.
     */
    @ApiModelProperty(value = "delay_delete_list_slot")
    private Integer delayDeleteListSlot;

    /**
     * Max slot
     */
    @ApiModelProperty(value = "max_slot")
    private Integer maxSlot;

}
