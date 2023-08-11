package io.openjob.server.scheduler.timer;

import io.openjob.common.OpenjobSpringContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Getter
@Setter
public class SchedulerTimerTask extends AbstractTimerTask {
    protected Long jobId;
    private Long dispatchVersion;
    protected String jobParamType;
    protected String jobParams;
    protected String jobExtendParamsType;
    protected String jobExtendParams;
    protected Long appid;
    protected Long workflowId;
    protected String processorType;
    protected String processorInfo;
    protected String executeType;
    protected Integer failRetryTimes;
    protected Integer failRetryInterval;
    protected Integer executeTimeout;
    protected Integer concurrency;
    protected String timeExpressionType;
    protected String timeExpression;

    private Integer executeStrategy;

    public SchedulerTimerTask(Long taskId, Long slotsId, Long expiration) {
        super(taskId, slotsId, expiration);
    }

    @Override
    public void run() {
        OpenjobSpringContext.getBean(SchedulerTimerExecutor.class).submit(this);
    }
}
