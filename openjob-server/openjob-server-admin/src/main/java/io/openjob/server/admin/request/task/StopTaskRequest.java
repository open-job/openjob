package io.openjob.server.admin.request.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class StopTaskRequest {
    @NotNull
    @ApiModelProperty(value = "Job instance id")
    private Long jobInstanceId;

    @NotNull
    @ApiModelProperty(value = "Job dispatch version")
    private Long dispatchVersion;

    @NotNull
    @ApiModelProperty(value = "Circle id")
    private Long circleId;

    @NotBlank
    @ApiModelProperty(value = "Task id")
    private String taskId;
}
