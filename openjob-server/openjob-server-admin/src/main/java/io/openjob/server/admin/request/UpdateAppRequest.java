package io.openjob.server.admin.request;

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

    @NotBlank
    @ApiModelProperty(value = "App name", required = true)
    private String name;

    @ApiModelProperty(value = "App desc", required = true)
    private String desc;
}
