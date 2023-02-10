package io.openjob.server.admin.request.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@ApiModel("Add app request")
@Data
public class UpdateAppRequest {

    @NotNull
    @ApiModelProperty(value = "App primary id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "App namespace id", required = true)
    private Long namespaceId;

    @NotBlank
    @ApiModelProperty(value = "App name", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "App desc", required = true)
    private String desc;

    @NotNull
    @ApiModelProperty(value = "App status.", required = true)
    private Integer status;
}
