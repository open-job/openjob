package io.openjob.server.admin.vo.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Data
public class ListJobVO {

    @ApiModelProperty(value = "Primary id", required = true)
    private Long id;

    @ApiModelProperty(value = "Namespace primary id")
    private Long namespaceId;

    @ApiModelProperty(value = "Workflow_id")
    private Long workflowId;

    @ApiModelProperty(value = "App primary id")
    private Long appId;

    @ApiModelProperty(value = "App name")
    private String appName;

    @ApiModelProperty(value = "Job name")
    private String name;

    @ApiModelProperty(value = "Job desc")
    private String description;

    @ApiModelProperty(value = "Job process type: bean/shell/python, default(bean)")
    private String processorType;

    @ApiModelProperty(value = "Job process info")
    private String processorInfo;

    @ApiModelProperty(value = "Shell processor type")
    private String shellProcessorType;

    @ApiModelProperty(value = "Shell processor info")
    private String shellProcessorInfo;

    @ApiModelProperty(value = "Kettle processor type")
    private String kettleProcessorType;

    @ApiModelProperty(value = "Kettle processor info")
    private String kettleProcessorInfo;

    @ApiModelProperty(value = "Sharding params")
    private String shardingParams;

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

    @ApiModelProperty(value = "Job execute timeout", required = true)
    private Integer executeTimeout;

    @ApiModelProperty(value = "Job execute concurrency, default(1)")
    private Integer concurrency;

    @ApiModelProperty(value = "Job type cron/second/delay, default(cron)")
    private String timeExpressionType;

    @ApiModelProperty(value = "Job type=cron need, cron express")
    private String timeExpression;

    @ApiModelProperty(value = "Job express value(secondDelay/fixedRate/oneTime)")
    private Long timeExpressionValue;

    @ApiModelProperty(value = "Job execute strategy")
    private Integer executeStrategy;

    @ApiModelProperty(value = "Job status 1=running 2=stop, default(1)")
    private Integer status;

    @ApiModelProperty(value = "Create time")
    private Long createTime;

    @ApiModelProperty(value = "Update time")
    private Long updateTime;
}
