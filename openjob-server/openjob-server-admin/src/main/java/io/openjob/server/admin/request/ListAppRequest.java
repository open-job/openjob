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
public class ListAppRequest {
    @ApiModelProperty(value = "List page.")
    private Integer page;

    @ApiModelProperty(value = "List size.")
    private Integer size;
}
