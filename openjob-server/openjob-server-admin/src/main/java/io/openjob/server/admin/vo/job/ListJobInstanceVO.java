package io.openjob.server.admin.vo.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class ListJobInstanceVO {
    @ApiModelProperty(value = "Job instance primary id")
    private Long id;

    @ApiModelProperty(value = "Namespace primary id")
    private Long namespaceId;

    @ApiModelProperty(value = "App primary id")
    private Long appId;

    @ApiModelProperty(value = "Job id")
    private Long jobId;

    @ApiModelProperty(value = "Job name")
    private String name;

    @ApiModelProperty(value = "Job desc")
    private String description;

    @ApiModelProperty(value = "Job process type: bean/shell/python, default(bean)")
    private String processorType;

    @ApiModelProperty(value = "Job process info")
    private String processorInfo;

    @ApiModelProperty(value = "Execute type: standalone, broadcast, MR")
    private String executeType;

    @ApiModelProperty(value = "Job params type")
    private String paramsType;

    @ApiModelProperty(value = "Job params")
    private String params;

    @ApiModelProperty(value = "Job extend params type")
    private String extendParamsType;

    @ApiModelProperty(value = "Job extend params")
    private String extendParams;

    @ApiModelProperty(value = "Job execute fail retry times")
    private Integer failRetryTimes;

    @ApiModelProperty(value = "Job execute fail retry interval")
    private Integer failRetryInterval;

    @ApiModelProperty(value = "Job execute concurrency, default(1)")
    private Integer concurrency;

    @ApiModelProperty(value = "Job type cron/second/delay, default(cron)")
    private String timeExpressionType;

    @ApiModelProperty(value = "Job type=cron need, cron express")
    private String timeExpression;

    @ApiModelProperty(value = "Job execute strategy")
    private Integer executeStrategy;

    @ApiModelProperty(value = "Job status 1=running 2=stop, default(1)")
    private Integer status;

    @ApiModelProperty(value = "Worker address")
    private String workerAddress;

    @ApiModelProperty(value = "Job execute complete time")
    private Long completeTime;

    @ApiModelProperty(value = "Job execute time")
    private Long executeTime;

    @ApiModelProperty(value = "Job execute last report time")
    private Long lastReportTime;

    @ApiModelProperty(value = "Job  create time")
    private Long createTime;
}
