package io.openjob.server.admin.vo.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @date 2022-11-13 18:14:41
 */
@Data
public class ListJobVO {

    @ApiModelProperty("Current page.")
    private Integer page;

    @ApiModelProperty("Job list.")
    private List<JobVO> jobList;

    @Data
    @ApiModel("Job item")
    public static class JobVO {

        @ApiModelProperty(value = "Namespace name", required = true)
        private String namespaceName;

        @ApiModelProperty(value = "App Name", required = true)
        private String appName;

        @ApiModelProperty(value = "Job name", required = true)
        private String name;

        @ApiModelProperty(value = "Job desc", required = true)
        private String description;

        @ApiModelProperty(value = "Job process type: bean/shell/python, default(bean)", required = true)
        private String processorType;

        @ApiModelProperty(value = "Job process info", required = true)
        private String processorInfo;

        @ApiModelProperty(value = "Execute type: standalone, broadcast, MR", required = true)
        private String executeType;

        @ApiModelProperty(value = "Job params", required = true)
        private String params;

        @ApiModelProperty(value = "Job execute fail retry times", required = true)
        private Integer failRetryTimes;

        @ApiModelProperty(value = "Job execute fail retry interval", required = true)
        private Integer failRetryInterval;

        @ApiModelProperty(value = "Job execute concurrency, default(1)", required = true)
        private Integer concurrency;

        @ApiModelProperty(value = "Job type cron/second/delay, default(cron)", required = true)
        private String timeExpressionType;

        @ApiModelProperty(value = "Job type=cron need, cron express", required = true)
        private String timeExpression;

        @ApiModelProperty(value = "Job status 1=running 2=stop, default(1)", required = true)
        private Integer status;
    }
}
