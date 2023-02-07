package io.openjob.server.admin.request.namespace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@ApiModel("Update namespace status request")
@Data
public class UpdateStatusNamespaceRequest {

    @NotNull
    @ApiModelProperty(value = "Update namespace id", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "New namespace status", required = true)
    private Integer status;
}
