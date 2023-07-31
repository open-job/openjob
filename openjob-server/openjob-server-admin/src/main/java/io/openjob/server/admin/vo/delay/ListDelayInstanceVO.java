package io.openjob.server.admin.vo.delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ListDelayInstanceVO {

    @ApiModelProperty(value = "ID", required = true)
    private Long id;

    @ApiModelProperty(value = "Namespace", required = true)
    private Long namespaceId;

    @ApiModelProperty(value = "Application name", required = true)
    private String appName;

    @ApiModelProperty(value = "Application", required = true)
    private Long appId;

    @ApiModelProperty(value = "Task id", required = true)
    private String taskId;

    @ApiModelProperty(value = "Topic", required = true)
    private String topic;

    @ApiModelProperty(value = "Delay id", required = true)
    private Long delayId;

    @ApiModelProperty(value = "Delay name", required = true)
    private String delayName;

    @ApiModelProperty(value = "Delay params", required = true)
    private String delayParams;

    @ApiModelProperty(value = "Delay extra", required = true)
    private String delayExtra;

    @ApiModelProperty(value = "Status", required = true)
    private Integer status;

    @ApiModelProperty(value = "Execute time", required = true)
    private Long executeTime;

    @ApiModelProperty(value = "Complete time", required = true)
    private Long completeTime;

    @ApiModelProperty(value = "Worker address", required = true)
    private String workerAddress;

    @ApiModelProperty(value = "Create time", required = true)
    private Long createTime;

}
