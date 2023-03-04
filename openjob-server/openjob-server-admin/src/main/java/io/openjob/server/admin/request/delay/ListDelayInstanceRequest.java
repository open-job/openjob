package io.openjob.server.admin.request.delay;

import io.openjob.server.admin.request.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListDelayInstanceRequest extends PageRequest {
    @ApiModelProperty(value = "Namespace id", required = true)
    private Long namespaceId;

    @ApiModelProperty(value = "Application id", required = true)
    private Long appId;

    @ApiModelProperty(value = "Topic", required = true)
    private String topic;

    @ApiModelProperty(value = "Status", required = true)
    private Integer status;

    @ApiModelProperty(value = "Task id", required = true)
    private String taskId;

    @ApiModelProperty(value = "Job begin time.")
    private Long beginTime;

    @ApiModelProperty(value = "Job end time.")
    private Long endTime;
}
