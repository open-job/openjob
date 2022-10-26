package io.openjob.server.scheduler.timer;

import io.openjob.common.request.ServerSubmitJobInstanceRequest;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public SchedulerTimerTask(Long taskId, Long slotsId, Long expiration) {
        super(taskId, slotsId, expiration);
    }

    @Override
    public void run() {
        Map<Long, List<WorkerDTO>> appWorkers = ClusterContext.getAppWorkers();
        if (!appWorkers.containsKey(this.appid)) {
            log.error("Worker do not exist! appid={}", this.appid);
            return;
        }

        try {
            ServerSubmitJobInstanceRequest submitReq = new ServerSubmitJobInstanceRequest();
            submitReq.setJobId(this.jobId);
            submitReq.setJobInstanceId(this.taskId);
            submitReq.setJobParams(this.jobParams);
            submitReq.setWorkflowId(this.workflowId);
            submitReq.setProcessorType(this.processorType);
            submitReq.setProcessorInfo(this.processorInfo);
            submitReq.setExecuteType(this.executeType);
            submitReq.setFailRetryTimes(this.failRetryTimes);
            submitReq.setFailRetryInterval(this.failRetryInterval);
            submitReq.setConcurrency(this.concurrency);
            submitReq.setTimeExpressionType(this.timeExpressionType);
            submitReq.setTimeExpression(this.timeExpression);
            WorkerDTO workerDTO = appWorkers.get(this.appid).get(0);

            List<String> workerAddresses = appWorkers.get(this.appid).stream()
                    .map(WorkerDTO::getAddress)
                    .collect(Collectors.toList());
            submitReq.setWorkerAddresses(workerAddresses);

            FutureUtil.ask(ServerUtil.getWorkerTaskMasterActor(workerDTO.getAddress()), submitReq, 10L);
            log.info("Task dispatch success! taskId={}", this.taskId);
        } catch (Throwable ex) {
            log.info("Task dispatch fail! taskId={} message={}", this.taskId, ex.getMessage());
        }
    }
}
