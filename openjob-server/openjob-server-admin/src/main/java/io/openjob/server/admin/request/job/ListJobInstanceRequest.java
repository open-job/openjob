package io.openjob.server.admin.request.job;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel()
public class ListJobInstanceRequest extends PageRequest {

    @ApiModelProperty(value = "Instance id")
    private Long id;

    @ApiModelProperty(value = "App primary id")
    private Long appId;

    @ApiModelProperty(value = "Namespace primary id")
    private Long namespaceId;

    @ApiModelProperty(value = "Job id")
    private Long jobId;

    @ApiModelProperty(value = "Job instance status")
    private Integer status;

    @ApiModelProperty(value = "Job begin time.")
    private Long beginTime;

    @ApiModelProperty(value = "Job end time.")
    private Long endTime;
}
