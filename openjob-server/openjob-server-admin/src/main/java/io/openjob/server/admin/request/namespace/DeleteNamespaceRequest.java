package io.openjob.server.admin.request.namespace;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class DeleteNamespaceRequest {

    @NotNull
    @ApiModelProperty(value = "Namespace primary id", required = true)
    private Long id;
}
