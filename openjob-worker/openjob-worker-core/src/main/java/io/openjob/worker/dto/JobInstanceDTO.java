package io.openjob.worker.dto;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Data
public class JobInstanceDTO {
    private Long jobId;
    private Long jobInstanceId;
    private String jobParamType;
    private String jobParams;
    private String jobExtendParamsType;
    private String jobExtendParams;
    private String executeType;
    private Long workflowId;
    private String processorType;
    private String processorInfo;
    private Integer failRetryTimes;
    private Integer failRetryInterval;
    private Integer executeTimeout;
    private Integer concurrency;
    private String timeExpressionType;
    private String timeExpression;
}
