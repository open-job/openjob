package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Data
public class WorkerJobInstanceTaskRequest {

    @NotNull
    @ApiModelProperty(value = "Job id.", required = true)
    private Long jobId;

    @NotNull
    @ApiModelProperty(value = "Job instance id.", required = true)
    private Long jobInstanceId;

    @NotNull
    @ApiModelProperty(value = "Only for second delay task.", required = true)
    private Long circleId;

    @NotBlank
    @ApiModelProperty(value = "Task unique id. like 'jobId_instanceId_taskId_circleId'", required = true)
    private String taskId;

    @NotBlank
    @ApiModelProperty(value = "Task parent unique id. like 'jobId_instanceId_taskId_circleId'", required = true)
    private String parentTaskId;

    @NotBlank
    @ApiModelProperty(value = "Task name. Only for map reduce task.", required = true)
    private String taskName;

    @NotNull
    @ApiModelProperty(value = "Task status.", required = true)
    private Integer status;

    @NotBlank
    @ApiModelProperty(value = "Task result.", required = true)
    private String result;

    @NotBlank
    @ApiModelProperty(value = "Task worker address", required = true)
    private String workerAddress;

    @NotNull
    @ApiModelProperty(value = "Task create time.", required = true)
    private Long createTime;

    @NotNull
    @ApiModelProperty(value = "Task update time.", required = true)
    private Long updateTime;
}
