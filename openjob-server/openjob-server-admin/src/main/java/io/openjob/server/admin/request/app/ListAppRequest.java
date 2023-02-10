package io.openjob.server.admin.request.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel
public class ListAppRequest {
    @NotNull
    @ApiModelProperty(value = "App namespace id", required = true)
    private Long namespaceId;

    @ApiModelProperty(value = "Search name.")
    private String name;

    @NotNull
    @ApiModelProperty(value = "List page.")
    private Integer page = 1;

    @NotNull
    @ApiModelProperty(value = "List size.")
    private Integer size = 10;
}
