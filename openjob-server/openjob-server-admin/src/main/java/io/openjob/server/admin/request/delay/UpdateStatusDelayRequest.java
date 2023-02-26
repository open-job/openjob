package io.openjob.server.admin.request.delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class UpdateStatusDelayRequest {
    @ApiModelProperty(value = "Delay primary id", required = true)
    private Long id;

    @ApiModelProperty(value = "status", required = true)
    private Integer status;
}
