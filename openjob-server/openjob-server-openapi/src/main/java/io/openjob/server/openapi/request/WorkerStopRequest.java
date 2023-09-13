package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerStopRequest {

    @NotBlank
    @ApiModelProperty("Worker key")
    private String workerKey;

    @NotBlank
    @ApiModelProperty("App name")
    private String appName;

    @NotBlank
    @ApiModelProperty("worker address")
    private String address;
}
