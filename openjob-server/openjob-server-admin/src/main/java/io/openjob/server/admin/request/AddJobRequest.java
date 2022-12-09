package io.openjob.server.admin.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@ApiModel
public class AddJobRequest {

    @NotNull
    @ApiModelProperty(value = "Namespace primary id", required = true)
    private Long namespaceId;

    @NotNull
    @ApiModelProperty(value = "App primary id", required = true)
    private Long appId;

    @NotBlank
    @ApiModelProperty(value = "Job name", required = true)
    private String name;

    @ApiModelProperty(value = "Job desc", required = true)
    private String description;

    @ApiModelProperty(value = "Job process type: bean/shell/python, default(bean)")
    private String processorType;

    @ApiModelProperty(value = "Job process info")
    private String processorInfo;

    @ApiModelProperty(value = "Execute type: standalone, broadcast, MR", required = true)
    private String executeType;

    @ApiModelProperty(value = "Job params", required = true)
    private String params;

    @ApiModelProperty(value = "Job execute fail retry times", required = true)
    private Integer failRetryTimes;

    @ApiModelProperty(value = "Job execute fail retry interval", required = true)
    private Integer failRetryInterval;

    @ApiModelProperty(value = "Job execute concurrency, default(1)")
    private Integer concurrency;

    @ApiModelProperty(value = "Job type cron/second/delay, default(cron)")
    private String timeExpressionType;

    @ApiModelProperty(value = "Job type=cron need, cron express")
    private String timeExpression;

    @ApiModelProperty(value = "Job status 1=running 2=stop, default(1)")
    private Integer status;
}
