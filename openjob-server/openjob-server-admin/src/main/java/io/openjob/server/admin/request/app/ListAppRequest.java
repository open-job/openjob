package io.openjob.server.admin.request.app;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class ListAppRequest extends PageRequest {
    @NotNull
    @ApiModelProperty(value = "App namespace id", required = true)
    private Long namespaceId;

    @ApiModelProperty(value = "Search name.")
    private String name;
}
