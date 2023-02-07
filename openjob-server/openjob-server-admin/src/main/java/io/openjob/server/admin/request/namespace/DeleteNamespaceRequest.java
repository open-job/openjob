package io.openjob.server.admin.request.namespace;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DeleteNamespaceRequest {

    @NotBlank
    @ApiModelProperty(value = "Namespace primary id", required = true)
    private Long id;
}
