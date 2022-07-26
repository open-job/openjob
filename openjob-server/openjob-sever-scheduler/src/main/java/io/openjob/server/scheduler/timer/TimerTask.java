package io.openjob.server.scheduler.timer;

import io.openjob.common.request.ServerSubmitJobInstanceRequest;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@Log4j2
public class TimerTask implements Runnable {
    private TimerTaskEntry timerTaskEntry;
    private Long jobId;

    private String jobParams;

    /**
     * Job instance id.
     */
    private Long taskId;
    private Long appid;
    private Long slotsId;
    private Long expiration;
    private Long workflowId;
    private String processorType;
    private String processorInfo;
    private String executeType;
    private Integer failRetryTimes;
    private Integer failRetryInterval;
    private Integer concurrency;
    private String timeExpressionType;
    private String timeExpression;

    /**
     * Timer task.
     *
     * @param taskId     taskId
     * @param slotsId    slotsId
     * @param expiration expiration
     */
    public TimerTask(Long taskId, Long slotsId, Long expiration) {
        this.expiration = expiration;
        this.slotsId = slotsId;
        this.taskId = taskId;
    }

    /**
     * Cancel timer task.
     */
    public void cancel() {
        synchronized (this) {
            if (Objects.nonNull(timerTaskEntry)) {
                timerTaskEntry.remove();
            }

            timerTaskEntry = null;
        }
    }

    /**
     * Set time task entry.
     *
     * @param entry entry
     */
    public void setTimerTaskEntry(TimerTaskEntry entry) {
        synchronized (this) {
            if (Objects.nonNull(timerTaskEntry) && timerTaskEntry != entry) {
                timerTaskEntry.remove();
            }

            timerTaskEntry = entry;
        }
    }

    /**
     * Getter for task id.
     *
     * @return Long
     */
    public Long getTaskId() {
        return taskId;
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
            submitReq.setFailRetryInterval(this.getFailRetryInterval());
            submitReq.setConcurrency(this.concurrency);
            submitReq.setTimeExpressionType(this.timeExpressionType);
            submitReq.setTimeExpression(this.timeExpression);
            WorkerDTO workerDTO = appWorkers.get(this.appid).get(0);
            FutureUtil.ask(ServerUtil.getWorkerTaskMasterActor(workerDTO.getAddress()), submitReq, 10L);
            log.info("Task dispatch success! taskId={}", this.taskId);
        } catch (Throwable ex) {
            log.info("Task dispatch fail! taskId={} message={}", this.taskId, ex.getMessage());
        }
    }
}
