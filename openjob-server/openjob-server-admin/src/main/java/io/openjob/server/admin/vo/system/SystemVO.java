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
public class SystemVO {


    @ApiModelProperty(value = "PK")
    private Integer id;

    @ApiModelProperty(value = "version")
    private String version;

    @ApiModelProperty(value = "cluster version")
    private Long clusterVersion;

    @ApiModelProperty(value = "cluster delay version")
    private Long clusterDelayVersion;

    @ApiModelProperty(value = "worker supervisor slot")
    private Integer workerSupervisorSlot;

    @ApiModelProperty(value = "delay zset slot")
    private Integer delayZsetSlot;

    @ApiModelProperty(value = "delay fail zset slot")
    private Integer delayFailZsetSlot;

    @ApiModelProperty(value = "delay add list slot")
    private Integer delayAddListSlot;

    @ApiModelProperty(value = "delay status list slot")
    private Integer delayStatusListSlot;

    @ApiModelProperty(value = "delay delete list slot")
    private Integer delayDeleteListSlot;

    @ApiModelProperty(value = "job keep days")
    private Integer jobKeepDays;

    @ApiModelProperty(value = "delay keep days")
    private Integer delayKeepDays;

    @ApiModelProperty(value = " sever keep days")
    private Integer serverKeepDays;

    @ApiModelProperty(value = "worker keep days")
    private Integer workerKeepDays;

    @ApiModelProperty(value = "max slot")
    private Integer maxSlot;
}
