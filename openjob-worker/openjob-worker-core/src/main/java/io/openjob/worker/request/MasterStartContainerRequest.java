package io.openjob.worker.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class MasterStartContainerRequest implements Serializable {
    private Long jobId;
    private Long jobInstanceId;
    private Long taskId;
    private String jobParams;
    private String executeType;
    private Long workflowId;
    private String processorType;
    private String processorInfo;
    private Integer failRetryTimes;
    private Integer failRetryInterval;
    private Integer concurrency;
    private String timeExpressionType;
    private String timeExpression;
    private String masterAkkaPath;

    private Object task;
    private List<String> workerAddresses;
}
