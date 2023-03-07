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
public class AdminSystemUpdateRequest {
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

