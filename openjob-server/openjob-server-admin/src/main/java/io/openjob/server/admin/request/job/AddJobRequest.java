package io.openjob.server.admin.request.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
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

    @ApiModelProperty(value = "Job desc")
    private String description;

    @NotBlank
    @ApiModelProperty(value = "Job process type: bean/shell/python, default(bean)", required = true)
    private String processorType;

    @NotBlank
    @ApiModelProperty(value = "Job process info", required = true)
    private String processorInfo;

    @NotBlank
    @ApiModelProperty(value = "Execute type: standalone, broadcast, MR", required = true)
    private String executeType;

    @NotBlank
    @ApiModelProperty(value = "Job params type", required = true)
    private String paramsType;

    @ApiModelProperty(value = "Job params")
    private String params;

    @ApiModelProperty(value = "Job extend params type")
    private String extendParamsType;

    @ApiModelProperty(value = "Job extend params")
    private String extendParams;

    @NotNull
    @ApiModelProperty(value = "Job execute fail retry times", required = true)
    private Integer failRetryTimes;

    @NotNull
    @ApiModelProperty(value = "Job execute fail retry interval", required = true)
    private Integer failRetryInterval;

    @NotNull
    @ApiModelProperty(value = "Job execute concurrency, default(1)", required = true)
    private Integer concurrency;

    @NotNull
    @ApiModelProperty(value = "Job type cron/second/delay, default(cron)", required = true)
    private String timeExpressionType;

    @NotNull
    @ApiModelProperty(value = "Job type=cron need, cron express", required = true)
    private String timeExpression;

    @NotNull
    @ApiModelProperty(value = "Job execute strategy", required = true)
    private Integer executeStrategy;

    @NotNull
    @ApiModelProperty(value = "Job status 1=running 2=stop, default(1)", required = true)
    private Integer status;
}
