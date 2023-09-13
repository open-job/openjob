package io.openjob.server.openapi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class ServerSubmitJobInstanceRequest {

    @NotNull
    @ApiModelProperty(value = "Job ID", required = true)
    private Long jobId;

    @NotNull
    @ApiModelProperty(value = "Job InstanceId ID", required = true)
    private Long jobInstanceId;

    @NotBlank
    @ApiModelProperty(value = "Job Param Type", required = true)
    private String jobParamType;

    @NotBlank
    @ApiModelProperty(value = "Job Params", required = true)
    private String jobParams;

    @NotBlank
    @ApiModelProperty(value = "Job Extend Params Type", required = true)
    private String jobExtendParamsType;

    @NotBlank
    @ApiModelProperty(value = "Job Extend Params", required = true)
    private String jobExtendParams;

    @ApiModelProperty(value = "Workflow Id", required = true)
    private Long workflowId;

    @NotBlank
    @ApiModelProperty(value = "Job process type: bean/shell/python, default(bean)", required = true)
    private String processorType;

    @NotBlank
    @ApiModelProperty(value = "Processor Info", required = true)
    private String processorInfo;

    @NotBlank
    @ApiModelProperty(value = "Execute type: standalone, broadcast, MR", required = true)
    private String executeType;

    @NotNull
    @ApiModelProperty(value = "Job execute fail retry times", required = true)
    private Integer failRetryTimes;

    @NotNull
    @ApiModelProperty(value = "Job execute fail retry interval", required = true)
    private Integer failRetryInterval;

    @NotNull
    @ApiModelProperty(value = "Job execute timeout", required = true)
    private Integer executeTimeout;

    @NotNull
    @ApiModelProperty(value = "Job execute concurrency, default(1)", required = true)
    private Integer concurrency;

    @NotBlank
    @ApiModelProperty(value = "Job type cron/second/delay, default(cron)", required = true)
    private String timeExpressionType;

    @ApiModelProperty(value = "Job cron express")
    private String timeExpression;
}
