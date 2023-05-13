package io.openjob.server.admin.request.namespace;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@ApiModel()
@Data
public class UpdateNamespaceRequest {

    @NotNull
    @ApiModelProperty(value = "Namespace primary id", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "Namespace name", required = true)
    private String name;
}
