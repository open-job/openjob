package io.openjob.server.admin.request.home;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class DelayChartRequest {
    @NotNull
    @ApiModelProperty(value = "App namespace id", required = true)
    private Long namespaceId;

    @ApiModelProperty(value = "Job begin time.")
    private Long beginTime;

    @ApiModelProperty(value = "Job end time.")
    private Long endTime;
}
