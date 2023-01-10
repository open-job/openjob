package io.openjob.server.scheduler.service;

import io.openjob.common.SpringContext;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.request.ServerSubmitJobInstanceRequest;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.common.dto.WorkerDTO;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.repository.constant.ExecuteStrategyEnum;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.JobInstanceLogDAO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.entity.JobInstanceLog;
import io.openjob.server.scheduler.timer.SchedulerTimerTask;
import io.openjob.server.scheduler.util.WorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Service
public class SchedulerTimerService {
    private final JobInstanceLogDAO jobInstanceLogDAO;
    private final JobInstanceDAO jobInstanceDAO;

    public SchedulerTimerService(JobInstanceLogDAO jobInstanceLogDAO, JobInstanceDAO jobInstanceDAO) {
        this.jobInstanceLogDAO = jobInstanceLogDAO;
        this.jobInstanceDAO = jobInstanceDAO;
    }

    public void run(SchedulerTimerTask task) {
        // Concurrency
        if (ExecuteStrategyEnum.CONCURRENCY.getStatus().equals(task.getExecuteStrategy())) {
            this.doRun(task);
            return;
        }

        // Discard after task.
        boolean isDiscard = ExecuteStrategyEnum.DISCARD.getStatus().equals(task.getExecuteStrategy());
        BooleanSupplier supplier = () -> {
            JobInstance jobInstance = this.jobInstanceDAO.getOneByIdAndStatus(task.getTaskId(), TaskStatusEnum.RUNNING.getStatus());
            return Objects.nonNull(jobInstance);
        };
        if (isDiscard && supplier.getAsBoolean()) {
            this.addInstanceLog(task.getJobId(), task.getTaskId(), " Discard after task");
            return;
        }

        // Overlay before task.
        this.doOverlay(task);
    }

    public void doRun(SchedulerTimerTask task) {
        try {
            ServerSubmitJobInstanceRequest submitReq = new ServerSubmitJobInstanceRequest();
            submitReq.setJobId(task.getJobId());
            submitReq.setJobInstanceId(task.getTaskId());
            submitReq.setJobParams(task.getJobParams());
            submitReq.setWorkflowId(task.getWorkflowId());
            submitReq.setProcessorType(task.getProcessorType());
            submitReq.setProcessorInfo(task.getProcessorInfo());
            submitReq.setExecuteType(task.getExecuteType());
            submitReq.setFailRetryTimes(task.getFailRetryTimes());
            submitReq.setFailRetryInterval(task.getFailRetryInterval());
            submitReq.setConcurrency(task.getConcurrency());
            submitReq.setTimeExpressionType(task.getTimeExpressionType());
            submitReq.setTimeExpression(task.getTimeExpression());

            WorkerDTO workerDTO = WorkerUtil.selectWorkerByAppId(task.getAppid());
            if (Objects.isNull(workerDTO)) {
                log.error("Worker do not exist! appid={}", task.getAppid());
                return;
            }

            FutureUtil.mustAsk(ServerUtil.getWorkerTaskMasterActor(workerDTO.getAddress()), submitReq, WorkerResponse.class, 3000L);
            log.info("Dispatch task success! taskId={}", task.getTaskId());

            // Update by dispatcher.
            SpringContext.getBean(SchedulerTimerService.class).updateByDispatcher(workerDTO.getAddress(), task.getJobId(), task.getTaskId(), InstanceStatusEnum.RUNNING, "Dispatch  task success!");
        } catch (Throwable ex) {
            // Update by dispatcher.
            this.addInstanceLog(task.getJobId(), task.getTaskId(), Arrays.toString(ex.getStackTrace()));
            log.info("Dispatch task fail! taskId={} message={}", task.getTaskId(), ex.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateByDispatcher(String workerAddress, Long jobId, Long instanceId, InstanceStatusEnum statusEnum, String message) {
        // Add instance log.
        this.addInstanceLog(jobId, instanceId, message);

        // Running to update.
        if (InstanceStatusEnum.RUNNING.getStatus().equals(statusEnum.getStatus())) {
            this.jobInstanceDAO.updateByRunning(instanceId, workerAddress, statusEnum);
        }
    }

    private void doOverlay(SchedulerTimerTask task) {

    }

    private void addInstanceLog(Long jobId, Long instanceId, String message) {
        Long timestamp = DateUtil.timestamp();
        JobInstanceLog jobInstanceLog = new JobInstanceLog();
        jobInstanceLog.setJobId(jobId);
        jobInstanceLog.setJobInstanceId(instanceId);
        jobInstanceLog.setMessage(message);
        jobInstanceLog.setDeleted(CommonConstant.NO);
        jobInstanceLog.setCreateTime(timestamp);
        jobInstanceLog.setDeleteTime(0L);
        jobInstanceLog.setUpdateTime(timestamp);
        this.jobInstanceLogDAO.save(jobInstanceLog);
    }
}
