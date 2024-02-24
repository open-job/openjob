package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class WorkerStopRequest {

    @NotBlank
    @ApiModelProperty("Worker unique id.")
    private String workerKey;

    @NotBlank
    @ApiModelProperty("App name")
    private String appName;

    @NotBlank
    @Pattern(regexp = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}$", message = "must be in format 'IP:PORT'")
    @ApiModelProperty("worker address")
    private String address;
}
