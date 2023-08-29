package io.openjob.server.admin.request.job;

import io.openjob.common.constant.HttpMethodEnum;
import io.openjob.common.constant.MediaTypeEnum;
import io.openjob.common.constant.RequestTypeEnum;
import io.openjob.common.constant.ResponseModeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author stelin swoft@qq.com
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

    @ApiModelProperty(value = "Job process info")
    private String processorInfo;

    @Valid
    @ApiModelProperty(value = "Http processor")
    private HttpProcessorRequest httpProcessor;

    @ApiModelProperty(value = "Shell processor info")
    private String shellProcessorInfo;

    @ApiModelProperty(value = "Shell processor type")
    private String shellProcessorType;

    @ApiModelProperty(value = "Kettle processor type")
    private String kettleProcessorType;

    @ApiModelProperty(value = "Kettle processor info")
    private String kettleProcessorInfo;

    @ApiModelProperty(value = "Sharding params")
    private String shardingParams;

    @NotBlank
    @ApiModelProperty(value = "Execute type: standalone, broadcast, MR", required = true)
    private String executeType;

    @ApiModelProperty(value = "Job params type")
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
    @ApiModelProperty(value = "Job execute timeout", required = true)
    private Integer executeTimeout;

    @NotNull
    @ApiModelProperty(value = "Job execute concurrency, default(1)", required = true)
    private Integer concurrency;

    @NotNull
    @ApiModelProperty(value = "Job type cron/second/delay, default(cron)", required = true)
    private String timeExpressionType;

    @ApiModelProperty(value = "Job cron express")
    private String timeExpression;

    @ApiModelProperty(value = "Job express value(secondDelay/fixedRate/oneTime)")
    private Long timeExpressionValue;

    @NotNull
    @ApiModelProperty(value = "Job execute strategy", required = true)
    private Integer executeStrategy;

    @NotNull
    @ApiModelProperty(value = "Job status 1=running 2=stop, default(1)", required = true)
    private Integer status;

    @Data
    public static class HttpProcessorRequest {
        @NotBlank
        @ApiModelProperty(value = "Http url")
        private String url;

        @NotBlank
        @ApiModelProperty(value = "Http method")
        private String method;

        @NotNull
        @ApiModelProperty(value = "Timeout(ms)")
        private Long timeout;

        @NotBlank
        @ApiModelProperty(value = "Content Type")
        private String contentType;

        @NotBlank
        @ApiModelProperty(value = "Body")
        private String body;

        @ApiModelProperty(value = "Cookie")
        private String cookie;

        @NotBlank
        @ApiModelProperty(value = "Response mode")
        private String responseMode;

        @ApiModelProperty(value = "JSON => key")
        private String key;

        @ApiModelProperty(value = "JSON => status,value or string")
        private String value;

        @NotBlank
        @ApiModelProperty(value = "Request type")
        private String requestType;
    }
}
