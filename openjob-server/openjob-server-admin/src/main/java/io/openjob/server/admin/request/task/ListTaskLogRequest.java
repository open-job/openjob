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
public class ListTaskLogRequest {
    @NotBlank
    @ApiModelProperty(value = "Task id", required = true)
    private String taskId;

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
