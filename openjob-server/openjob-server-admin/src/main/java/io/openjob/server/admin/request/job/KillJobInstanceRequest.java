package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class KillJobInstanceRequest {
    @ApiModelProperty(value = "Job instance primary id")
    private Long id;
}
