package io.openjob.server.admin.vo.Delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ListDelayVO {
    @ApiModelProperty(value = "Delay primary id", required = true)
    private Long id;

    @ApiModelProperty(value = "Namespace id", required = true)
    private Long namespace_id;

    @ApiModelProperty(value = "Application id", required = true)
    private Long appId;

    @ApiModelProperty(value = "Name", required = true)
    private String name;

    @ApiModelProperty(value = "Topic", required = true)
    private String topic;

    @ApiModelProperty(value = "Description", required = true)
    private String description;

    @ApiModelProperty(value = "", required = true)
    private String processorInfo;

    @ApiModelProperty(value = "Fail retry times", required = true)
    private Integer failRetryTimes;

    @ApiModelProperty(value = "fail retry interval", required = true)
    private Integer failRetryInterval;

    @ApiModelProperty(value = "status", required = true)
    private Integer status;

    @ApiModelProperty(value = "Execute timeout", required = true)
    private Integer executeTimeout;

    @ApiModelProperty(value = "Concurrency", required = true)
    private Integer concurrency;

    @ApiModelProperty(value = "Blocking size", required = true)
    private Integer blockingSize;

    @ApiModelProperty(value = "Create time", required = true)
    private Long createTime;
}
