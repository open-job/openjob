package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author zhenghongyang sakuraovq@gmail.com
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
    @Pattern(regexp = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}$", message = "must be in format 'IP:PORT'")
    @ApiModelProperty("Worker address.")
    private String address;

    @NotBlank
    @ApiModelProperty("Worker version")
    private String version;

    @NotNull
    @ApiModelProperty(" Running job instance ids.")
    private List<Long> runningJobInstanceIds;
}
