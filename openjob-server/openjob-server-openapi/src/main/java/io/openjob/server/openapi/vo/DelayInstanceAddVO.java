package io.openjob.server.openapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class DelayInstanceAddVO {

    @ApiModelProperty("Delay task unique id. If is null or blank, will to auto generate.")
    private String taskId;
}
