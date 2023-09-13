package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ListProcessorLogRequest {

    @NotNull
    @ApiModelProperty(value = "Job instance id", required = true)
    private Long jobId;

    @NotNull
    @ApiModelProperty(value = "Dispatch version", required = true)
    private Long dispatchVersion;

    @NotNull
    @ApiModelProperty(value = "Job instance id", required = true)
    private Long jobInstanceId;

    @NotBlank
    @ApiModelProperty(value = "Execute type: standalone, broadcast, MR", required = true)
    private String executeType;

    @NotNull
    @ApiModelProperty(value = "Status", required = true)
    private Integer status;

    @NotNull
    @ApiModelProperty(value = "Loading", required = true)
    private Integer loading;

    @ApiModelProperty(value = "Processor time, default is zero.", required = true)
    private Long time = 0L;

    @ApiModelProperty(value = "Page size", required = true)
    private Integer size = 20;
}
