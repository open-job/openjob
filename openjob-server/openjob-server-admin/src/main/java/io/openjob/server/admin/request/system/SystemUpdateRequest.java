package io.openjob.server.admin.request.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rikiy
 * @since 1.0.0
 */
@Data
@ApiModel(value = "AdminSystemUpdateRequest", description = "AdminSystem Update  Request")
public class SystemUpdateRequest {
    @NotNull
    @ApiModelProperty(value = "delay zset slot")
    private Integer delayZsetSlot;

    @NotNull
    @ApiModelProperty(value = "delay fail zset slot")
    private Integer delayFailZsetSlot;

    @NotNull
    @ApiModelProperty(value = "delay add list slot")
    private Integer delayAddListSlot;

    @NotNull
    @ApiModelProperty(value = "delay status list slot")
    private Integer delayStatusListSlot;

    @NotNull
    @ApiModelProperty(value = "delay delete list slot")
    private Integer delayDeleteListSlot;

    @NotNull
    @ApiModelProperty(value = "max slot")
    private Integer maxSlot;

    @NotNull
    @ApiModelProperty(value = "job keep days")
    private Integer jobKeepDays;

    @NotNull
    @ApiModelProperty(value = "delay keep days")
    private Integer delayKeepDays;

    @NotNull
    @ApiModelProperty(value = " sever keep days")
    private Integer serverKeepDays;

    @NotNull
    @ApiModelProperty(value = "worker keep days")
    private Integer workerKeepDays;
}

