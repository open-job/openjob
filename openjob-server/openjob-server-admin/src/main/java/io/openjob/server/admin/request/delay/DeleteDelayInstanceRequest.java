package io.openjob.server.admin.request.delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class DeleteDelayInstanceRequest {
    @ApiModelProperty(value = "Delay task id", required = true)
    private String taskId;
}
