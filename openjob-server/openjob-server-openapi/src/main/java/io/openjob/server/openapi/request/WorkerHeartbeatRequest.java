package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerHeartbeatRequest {

    @NotNull
    @ApiModelProperty("App id")
    private Long appId;

    @NotBlank
    @ApiModelProperty("App name")
    private String appName;

    @NotBlank
    @ApiModelProperty("Worker internet address")
    private String address;

    @NotBlank
    @ApiModelProperty("Worker version")
    private String version;

    @NotNull
    @ApiModelProperty(" Running job instance ids.")
    private List<Long> runningJobInstanceIds;
}
