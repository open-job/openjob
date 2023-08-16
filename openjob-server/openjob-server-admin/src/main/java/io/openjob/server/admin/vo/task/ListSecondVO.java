package io.openjob.server.admin.vo.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Data
public class ListSecondVO {
    @ApiModelProperty(value = "Pk id")
    private Long id;

    @ApiModelProperty(value = "Job id")
    private Long jobId;

    @ApiModelProperty(value = "Instance id")
    private Long jobInstanceId;

    @ApiModelProperty(value = "Circle id")
    private Long circleId;

    @ApiModelProperty(value = "Task id")
    private String taskId;

    @ApiModelProperty(value = "Task name")
    private String taskName;

    @ApiModelProperty(value = "Status")
    private Integer status;

    @ApiModelProperty(value = "Result")
    private String result;

    @ApiModelProperty(value = "Child count")
    private Long childCount;

    @ApiModelProperty(value = "Worker address")
    private String workerAddress;

    @ApiModelProperty(value = "Create time")
    private Long createTime;
}
