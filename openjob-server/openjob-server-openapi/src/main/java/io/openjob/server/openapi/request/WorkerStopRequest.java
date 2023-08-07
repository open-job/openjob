package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerStopRequest {

    @ApiModelProperty("Worker key")
    private String workerKey;

    @ApiModelProperty("App name")
    private String appName;

    @ApiModelProperty("worker address")
    private String address;
}
