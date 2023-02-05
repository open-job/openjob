package io.openjob.server.admin.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel
public class ListNamespaceRequest {
    @ApiModelProperty(value = "Search name.")
    private String name;

    @ApiModelProperty(value = "List page.")
    private Integer page = 1;

    @ApiModelProperty(value = "List size.")
    private Integer size = 10;
}
