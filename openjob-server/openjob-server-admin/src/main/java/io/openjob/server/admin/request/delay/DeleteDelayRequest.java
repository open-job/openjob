package io.openjob.server.admin.request.delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DeleteDelayRequest {
    @ApiModelProperty(value = "Primary id", required = true)
    private Long id;
}
