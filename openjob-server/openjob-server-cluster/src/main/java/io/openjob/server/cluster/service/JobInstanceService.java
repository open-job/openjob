package io.openjob.server.cluster.service;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.request.WorkerJobInstanceLogRequest;
import io.openjob.common.request.WorkerJobInstanceStatusRequest;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.executor.WorkerJobInstanceExecutor;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.JobInstanceLogDAO;
import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import io.openjob.server.repository.entity.JobInstanceLog;
import io.openjob.server.repository.entity.JobInstanceTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Service
@Log4j2
public class JobInstanceService {
    private final JobInstanceTaskDAO jobInstanceTaskDAO;
    private final JobInstanceLogDAO jobInstanceLogDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final WorkerJobInstanceExecutor workerJobInstanceExecutor;

    @Autowired
    public JobInstanceService(JobInstanceTaskDAO jobInstanceTaskDAO,
                              JobInstanceLogDAO jobInstanceLogDAO,
                              JobInstanceDAO jobInstanceDAO,
                              WorkerJobInstanceExecutor workerJobInstanceExecutor) {
        this.jobInstanceTaskDAO = jobInstanceTaskDAO;
        this.jobInstanceLogDAO = jobInstanceLogDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.workerJobInstanceExecutor = workerJobInstanceExecutor;
    }

    @Transactional(rollbackFor = Exception.class, timeout = 1)
    public void handleInstanceStatus(WorkerJobInstanceStatusRequest statusRequest) {
        this.workerJobInstanceExecutor.submit(statusRequest);
    }

    /**
     * Handle instance status.
     *
     * @param statusRequest status request.
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleConsumerInstanceStatus(WorkerJobInstanceStatusRequest statusRequest) {
        Long start = DateUtil.timestamp();
        // First page to update job instance status.
        if (CommonConstant.FIRST_PAGE.equals(statusRequest.getPage())) {
            this.jobInstanceDAO.updateStatusById(statusRequest.getJobInstanceId(), statusRequest.getStatus());
        }

        // Save job instance task
        List<JobInstanceTask> taskList = new ArrayList<>();
        statusRequest.getTaskRequestList().forEach(t -> {
            JobInstanceTask jobInstanceTask = new JobInstanceTask();
            jobInstanceTask.setJobId(t.getJobId());
            jobInstanceTask.setJobInstanceId(t.getJobInstanceId());
            jobInstanceTask.setCircleId(t.getCircleId());
            jobInstanceTask.setTaskId(t.getTaskId());
            jobInstanceTask.setParentTaskId(t.getParentTaskId());
            jobInstanceTask.setTaskName(t.getTaskName());
            jobInstanceTask.setStatus(t.getStatus());
            jobInstanceTask.setResult(t.getResult());
            jobInstanceTask.setWorkerAddress(t.getWorkerAddress());
            jobInstanceTask.setDeleted(CommonConstant.NO);
            jobInstanceTask.setDeleteTime(0L);
            jobInstanceTask.setCreateTime(t.getCreateTime());
            jobInstanceTask.setUpdateTime(t.getUpdateTime());
            taskList.add(jobInstanceTask);
        });

        try {
            this.jobInstanceTaskDAO.batchSave(taskList);
        } catch (DataIntegrityViolationException | UnexpectedRollbackException exception) {
            log.warn("Data has been saved! {}", taskList.stream().map(JobInstanceTask::getTaskId).collect(Collectors.toList()));
        }

        System.out.println("timetime="+(DateUtil.timestamp()-start));
    }

    /**
     * Handle instance log.
     *
     * @param logRequest log request.
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleInstanceLog(WorkerJobInstanceLogRequest logRequest) {
        // Update job instance status.
        this.jobInstanceDAO.updateStatusById(logRequest.getJobInstanceId(), logRequest.getStatus());

        JobInstanceLog jobInstanceLog = new JobInstanceLog();
        jobInstanceLog.setJobId(logRequest.getJobId());
        jobInstanceLog.setJobInstanceId(logRequest.getJobInstanceId());
        jobInstanceLog.setMessage(logRequest.getMessage());
        jobInstanceLog.setDeleted(CommonConstant.NO);
        jobInstanceLog.setDeleteTime(DateUtil.timestamp());
        jobInstanceLog.setCreateTime(logRequest.getTime());
        jobInstanceLog.setUpdateTime(logRequest.getTime());
        this.jobInstanceLogDAO.save(jobInstanceLog);
    }
}
