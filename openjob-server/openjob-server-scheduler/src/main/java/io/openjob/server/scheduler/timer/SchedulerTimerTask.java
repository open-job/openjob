package io.openjob.server.scheduler.timer;

import io.openjob.common.SpringContext;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.request.ServerSubmitJobInstanceRequest;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.scheduler.service.SchedulerTimerService;
import io.openjob.server.scheduler.util.WorkerUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Getter
@Setter
public class SchedulerTimerTask extends AbstractTimerTask {
    protected Long jobId;
    protected String jobParams;
    protected Long appid;
    protected Long workflowId;
    protected String processorType;
    protected String processorInfo;
    protected String executeType;
    protected Integer failRetryTimes;
    protected Integer failRetryInterval;
    protected Integer concurrency;
    protected String timeExpressionType;
    protected String timeExpression;

    private Integer executeStrategy;

    public SchedulerTimerTask(Long taskId, Long slotsId, Long expiration) {
        super(taskId, slotsId, expiration);
    }

    @Override
    public void run() {
        // Dispatch task to run.
        SpringContext.getBean(SchedulerTimerService.class).run(this);
    }
}
