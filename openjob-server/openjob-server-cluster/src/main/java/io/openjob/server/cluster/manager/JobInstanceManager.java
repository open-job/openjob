package io.openjob.server.cluster.manager;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.FailStatusEnum;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.server.alarm.constant.AlarmEventEnum;
import io.openjob.server.alarm.dto.AlarmEventDTO;
import io.openjob.server.alarm.event.AlarmEvent;
import io.openjob.server.alarm.event.AlarmEventPublisher;
import io.openjob.server.cluster.dto.WorkerJobInstanceStatusReqDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceStatusRespDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskBatchReqDTO;
import io.openjob.server.cluster.dto.WorkerJobInstanceTaskBatchRespDTO;
import io.openjob.server.cluster.executor.WorkerJobInstanceExecutor;
import io.openjob.server.cluster.executor.WorkerJobInstanceTaskExecutor;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import io.openjob.server.repository.entity.JobInstanceTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Slf4j
@Component
public class JobInstanceManager {
    private final JobInstanceTaskDAO jobInstanceTaskDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final WorkerJobInstanceExecutor workerJobInstanceExecutor;
    private final WorkerJobInstanceTaskExecutor workerJobInstanceTaskExecutor;

    @Autowired
    public JobInstanceManager(JobInstanceTaskDAO jobInstanceTaskDAO,
                              JobInstanceDAO jobInstanceDAO,
                              WorkerJobInstanceExecutor workerJobInstanceExecutor,
                              WorkerJobInstanceTaskExecutor workerJobInstanceTaskExecutor) {
        this.jobInstanceTaskDAO = jobInstanceTaskDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.workerJobInstanceExecutor = workerJobInstanceExecutor;
        this.workerJobInstanceTaskExecutor = workerJobInstanceTaskExecutor;
    }

    public WorkerJobInstanceStatusRespDTO handleInstanceStatus(WorkerJobInstanceStatusReqDTO statusRequest) {
        this.workerJobInstanceExecutor.submit(statusRequest);
        return new WorkerJobInstanceStatusRespDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public void handleConsumerInstanceStatus(List<WorkerJobInstanceStatusReqDTO> statusList) {
        // Update status
        statusList.forEach(s -> {
            this.jobInstanceDAO.updateStatusById(s.getJobInstanceId(), s.getStatus(), s.getFailStatus());
            this.addAlarmEvent(s);
        });
    }

    public WorkerJobInstanceTaskBatchRespDTO handleInstanceTasks(WorkerJobInstanceTaskBatchReqDTO taskBatchRequest) {
        this.workerJobInstanceTaskExecutor.submit(taskBatchRequest);
        return new WorkerJobInstanceTaskBatchRespDTO();
    }

    /**
     * Handle instance status.
     *
     * @param taskList task list
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleConsumerInstanceTasks(List<WorkerJobInstanceTaskBatchReqDTO> taskList) {
        List<JobInstanceTask> saveList = new ArrayList<>();
        taskList.forEach(l -> l.getTaskRequestList().forEach(t -> {
            JobInstanceTask jobInstanceTask = new JobInstanceTask();
            jobInstanceTask.setJobId(t.getJobId());
            jobInstanceTask.setJobInstanceId(t.getJobInstanceId());
            jobInstanceTask.setDispatchVersion(t.getDispatchVersion());
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
            saveList.add(jobInstanceTask);
        }));

        try {
            this.jobInstanceTaskDAO.batchSave(saveList);
        } catch (DataIntegrityViolationException | UnexpectedRollbackException exception) {
            log.warn("Data has been saved! {}", taskList);
        }
    }

    protected void addAlarmEvent(WorkerJobInstanceStatusReqDTO statusRequest) {
        if (InstanceStatusEnum.isFailed(statusRequest.getStatus())) {
            AlarmEventDTO alarmEventDTO = new AlarmEventDTO();
            alarmEventDTO.setJobUniqueId(String.valueOf(statusRequest.getJobId()));
            alarmEventDTO.setInstanceId(String.valueOf(statusRequest.getJobInstanceId()));

            // Fail status
            if (FailStatusEnum.isExecuteTimeout(statusRequest.getFailStatus())) {
                alarmEventDTO.setName(AlarmEventEnum.JOB_EXECUTE_TIMEOUT.getEvent());
            } else {
                alarmEventDTO.setName(AlarmEventEnum.JOB_EXECUTE_FAIL.getEvent());
            }

            // Event message
            alarmEventDTO.setMessage(Optional.ofNullable(statusRequest.getResult()).orElse(""));
            AlarmEventPublisher.publishEvent(new AlarmEvent(alarmEventDTO));
        }
    }
}
