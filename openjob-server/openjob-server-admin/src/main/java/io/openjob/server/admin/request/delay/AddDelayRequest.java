package io.openjob.server.admin.request.delay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class AddDelayRequest {
    @NotNull
    @ApiModelProperty(value = "Namespace id", required = true)
    private Long namespaceId;

    @NotNull
    @ApiModelProperty(value = "Application id", required = true)
    private Long appId;

    @NotBlank
    @ApiModelProperty(value = "Name", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(value = "Topic", required = true)
    private String topic;

    @ApiModelProperty(value = "Description", required = true)
    private String description;

    @NotBlank
    @ApiModelProperty(value = "Processor", required = true)
    private String processorInfo;

    @NotNull
    @ApiModelProperty(value = "Fail retry times", required = true)
    private Integer failRetryTimes;

    @NotNull
    @ApiModelProperty(value = "fail retry interval", required = true)
    private Integer failRetryInterval;

    @NotNull
    @ApiModelProperty(value = "Execute timeout", required = true)
    private Integer executeTimeout;

    @NotNull
    @ApiModelProperty(value = "Concurrency", required = true)
    private Integer concurrency;

    @NotNull
    @ApiModelProperty(value = "Blocking size", required = true)
    private Integer blockingSize;

    @NotNull
    @ApiModelProperty(value = "Fail topic enable", required = true)
    private Integer failTopicEnable;

    @NotNull
    @ApiModelProperty(value = "Fail topic concurrency", required = true)
    private Integer failTopicConcurrency;
}
