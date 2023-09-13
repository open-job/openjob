package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class ServerStopJobInstanceRequest {

    @NotNull
    @ApiModelProperty(value = "Job ID", required = true)
    private Long jobId;

    @NotNull
    @ApiModelProperty(value = "Job Instance ID", required = true)
    private Long jobInstanceId;
}
