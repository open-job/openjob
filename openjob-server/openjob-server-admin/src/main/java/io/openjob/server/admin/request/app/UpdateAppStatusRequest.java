package io.openjob.server.admin.request.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class UpdateAppStatusRequest {
    @NotNull
    @ApiModelProperty(value = "App primary id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "App status.", required = true)
    private Integer status;
}
