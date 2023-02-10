package io.openjob.server.admin.request.namespace;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class ListNamespaceRequest extends PageRequest {
    @ApiModelProperty(value = "Search name.")
    private String name;

    @ApiModelProperty(value = "List page.")
    private Integer page = 1;

    @ApiModelProperty(value = "List size.")
    private Integer size = 10;
}
