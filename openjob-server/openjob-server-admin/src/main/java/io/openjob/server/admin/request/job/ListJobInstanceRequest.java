package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel()
public class ListJobInstanceRequest {
    @ApiModelProperty(value = "App primary id")
    private Long appId;

    @ApiModelProperty(value = "Job id")
    private String jobId;

    @ApiModelProperty(value = "Job instance status")
    private Integer status;

    @ApiModelProperty(value = "Job begin time.")
    private Long beginTime;

    @ApiModelProperty(value = "Job end time.")
    private Long endTime;
}
