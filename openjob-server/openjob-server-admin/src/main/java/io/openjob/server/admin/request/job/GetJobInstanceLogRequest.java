package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class GetJobInstanceLogRequest {
    @NotNull
    @ApiModelProperty(value = "Job instance id", required = true)
    private Long jobInstanceId;
}
