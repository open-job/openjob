package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerStartRequest {

    @NotBlank
    @ApiModelProperty("Worker key")
    private String workerKey;

    @NotBlank
    @ApiModelProperty("App name")
    private String appName;

    @NotBlank
    @ApiModelProperty("Worker address")
    private String address;

    @NotBlank
    @ApiModelProperty("Worker agent version")
    private String version;

    @NotBlank
    @ApiModelProperty("Worker current protocol type")
    private String protocolType;

    @ApiModelProperty("metric")
    private Metric metric;

    @Data
    public static class Metric {

    }
}
