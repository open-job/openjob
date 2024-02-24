package io.openjob.server.openapi.request;

import io.openjob.common.constant.ProtocolTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class WorkerStartRequest {

    @NotBlank
    @ApiModelProperty("Worker unique id.")
    private String workerKey;

    @NotBlank
    @ApiModelProperty("App name")
    private String appName;

    @NotBlank
    @Pattern(regexp = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}$", message = "must be in format 'IP:PORT'")
    @ApiModelProperty("Worker address")
    private String address;

    @NotBlank
    @ApiModelProperty("Worker agent version")
    private String version;

    @ApiModelProperty("Worker current protocol type, http is default.")
    private String protocolType = ProtocolTypeEnum.HTTP.getType();

    @ApiModelProperty("metric")
    private Metric metric;

    @Data
    public static class Metric {

    }
}
