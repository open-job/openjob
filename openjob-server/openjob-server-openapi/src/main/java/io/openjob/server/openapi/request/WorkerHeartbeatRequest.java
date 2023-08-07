package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerHeartbeatRequest {

    @ApiModelProperty("App id")
    private Long appId;

    @ApiModelProperty("App name")
    private String appName;

    @ApiModelProperty("Worker internet address")
    private String address;

    @ApiModelProperty("Worker version")
    private String version;

    @ApiModelProperty(" Running job instance ids.")
    private List<Long> runningJobInstanceIds;
}
