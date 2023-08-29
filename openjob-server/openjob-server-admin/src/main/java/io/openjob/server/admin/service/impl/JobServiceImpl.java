package io.openjob.server.admin.service.impl;

import com.google.common.collect.Lists;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.common.constant.FailStatusEnum;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.ProcessorTypeEnum;
import io.openjob.common.constant.ResponseModeEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.dto.ShellProcessorDTO;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.JsonUtil;
import io.openjob.server.admin.constant.AdminConstant;
import io.openjob.server.admin.constant.CodeEnum;
import io.openjob.server.admin.request.job.AddJobRequest;
import io.openjob.server.admin.request.job.DeleteJobRequest;
import io.openjob.server.admin.request.job.ExecuteJobRequest;
import io.openjob.server.admin.request.job.ListJobRequest;
import io.openjob.server.admin.request.job.TimeExpressionRequest;
import io.openjob.server.admin.request.job.UpdateJobRequest;
import io.openjob.server.admin.request.job.UpdateJobStatusRequest;
import io.openjob.server.admin.service.JobService;
import io.openjob.server.admin.vo.job.AddJobVO;
import io.openjob.server.admin.vo.job.DeleteJobVO;
import io.openjob.server.admin.vo.job.ExecuteJobVO;
import io.openjob.server.admin.vo.job.ListJobVO;
import io.openjob.server.admin.vo.job.TimeExpressionVO;
import io.openjob.server.admin.vo.job.UpdateJobStatusVO;
import io.openjob.server.admin.vo.job.UpdateJobVO;
import io.openjob.server.cluster.data.RefreshData;
import io.openjob.server.common.cron.CronExpression;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.repository.constant.JobStatusEnum;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dto.JobPageDTO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.scheduler.dto.JobExecuteRequestDTO;
import io.openjob.server.scheduler.dto.JobInstanceStopRequestDTO;
import io.openjob.server.scheduler.scheduler.JobInstanceScheduler;
import io.openjob.server.scheduler.service.JobSchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhenghongyang <sakuraovq@gmail.com>
 * @since 1.0.0
 */
@Slf4j
@Component
public class JobServiceImpl implements JobService {

    private final JobDAO jobDAO;
    private final AppDAO appDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final RefreshData refreshData;
    private final JobSchedulingService jobSchedulingService;
    private final JobInstanceScheduler jobInstanceScheduler;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO,
                          AppDAO appDAO,
                          JobInstanceDAO jobInstanceDAO,
                          RefreshData refreshData,
                          JobSchedulingService jobSchedulingService,
                          JobInstanceScheduler jobInstanceScheduler) {
        this.jobDAO = jobDAO;
        this.appDAO = appDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.refreshData = refreshData;
        this.jobSchedulingService = jobSchedulingService;
        this.jobInstanceScheduler = jobInstanceScheduler;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddJobVO add(AddJobRequest addJobRequest) {
        // Valid and init job.
        this.validAndInitJob(addJobRequest);

        if (!TimeExpressionTypeEnum.isCron(addJobRequest.getTimeExpressionType())) {
            addJobRequest.setTimeExpression(String.valueOf(addJobRequest.getTimeExpressionValue()));
        }

        // Running status to parse next execute time.
        Job job = BeanMapperUtil.map(addJobRequest, Job.class);
        if (TimeExpressionTypeEnum.isCron(addJobRequest.getTimeExpressionType()) && JobStatusEnum.isRunning(job.getStatus())) {
            job.setNextExecuteTime(this.parseTimeExpression(job.getTimeExpression()));
        } else if (TimeExpressionTypeEnum.isOneTime(addJobRequest.getTimeExpressionType())) {
            job.setNextExecuteTime(Long.valueOf(addJobRequest.getTimeExpression()));
        } else if (TimeExpressionTypeEnum.isSecondDelay(addJobRequest.getTimeExpressionType())) {
            job.setNextExecuteTime(DateUtil.timestamp());
        }

        long id = this.jobDAO.save(job);
        job.setId(id);

        // Second delay to create job instance
        if (TimeExpressionTypeEnum.isSecondDelay(addJobRequest.getTimeExpressionType()) && JobStatusEnum.isRunning(job.getStatus())) {
            this.createJobInstance(job);
        }

        return new AddJobVO().setId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateJobVO update(UpdateJobRequest updateJobRequest) {
        // Valid and init job.
        this.validAndInitJob(updateJobRequest);

        if (!TimeExpressionTypeEnum.isCron(updateJobRequest.getTimeExpressionType())) {
            updateJobRequest.setTimeExpression(String.valueOf(updateJobRequest.getTimeExpressionValue()));
        }

        // Job
        Job updateJob = BeanMapperUtil.map(updateJobRequest, Job.class);

        // Condition
        boolean isUpdateCron = TimeExpressionTypeEnum.isCron(updateJob.getTimeExpressionType());
        boolean isRunningStatus = JobStatusEnum.isRunning(updateJob.getStatus());

        // Parse time expression
        if (isRunningStatus && isUpdateCron) {
            updateJob.setNextExecuteTime(this.parseTimeExpression(updateJob.getTimeExpression()));
        } else if (TimeExpressionTypeEnum.isOneTime(updateJobRequest.getTimeExpressionType())) {
            updateJob.setNextExecuteTime(Long.valueOf(updateJobRequest.getTimeExpression()));
        }

        // Update
        this.jobDAO.update(updateJob);

        // Second delay
        if (TimeExpressionTypeEnum.isSecondDelay(updateJobRequest.getTimeExpressionType())) {
            this.updateJobBySecond(updateJob);
        }

        // Refresh cluster version.
        this.refreshData.refreshSystemClusterVersion();
        return new UpdateJobVO();
    }

    @Override
    public DeleteJobVO delete(DeleteJobRequest deleteJobRequest) {
        if (Objects.nonNull(this.jobInstanceDAO.getFirstByJobId(deleteJobRequest.getId()))) {
            CodeEnum.JOB_DELETE_INVALID.throwException();
        }

        this.jobDAO.updateByStatusOrDeleted(deleteJobRequest.getId(), null, CommonConstant.YES, null);
        return new DeleteJobVO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateJobStatusVO updateStatus(UpdateJobStatusRequest request) {
        Long nextExecuteTime = null;
        Job originJob = this.jobDAO.getById(request.getId());
        if (TimeExpressionTypeEnum.isCron(originJob.getTimeExpressionType()) && JobStatusEnum.isRunning(request.getStatus())) {
            nextExecuteTime = this.parseTimeExpression(originJob.getTimeExpression());
        }

        this.jobDAO.updateByStatusOrDeleted(request.getId(), request.getStatus(), null, nextExecuteTime);

        // Second delay
        if (TimeExpressionTypeEnum.isSecondDelay(originJob.getTimeExpressionType())) {
            Job newJob = this.jobDAO.getById(request.getId());
            this.updateJobBySecond(newJob);
        }

        return new UpdateJobStatusVO();
    }

    @Override
    public ExecuteJobVO execute(ExecuteJobRequest request) {
        this.jobSchedulingService.execute(BeanMapperUtil.map(request, JobExecuteRequestDTO.class));
        return new ExecuteJobVO();
    }

    @Override
    public TimeExpressionVO timeExpression(TimeExpressionRequest request) {
        List<Long> timeList = Lists.newArrayList();
        TimeExpressionVO timeExpressionVO = new TimeExpressionVO();

        try {
            Date date = null;
            for (int i = 0; i < AdminConstant.MAX_TIME_EXPRESSION; i++) {
                date = Optional.ofNullable(date).orElseGet(Date::new);
                CronExpression cronExpression = new CronExpression(request.getTimeExpression());
                long nextTime = cronExpression.getNextValidTimeAfter(date).toInstant().getEpochSecond();
                timeList.add(nextTime);
                date = new Date(nextTime * 1000L);
            }

            timeExpressionVO.setValid(CommonConstant.YES);
        } catch (ParseException e) {
            timeExpressionVO.setValid(CommonConstant.NO);
        }

        timeExpressionVO.setList(timeList);
        return timeExpressionVO;
    }

    @Override
    public PageVO<ListJobVO> getPageList(ListJobRequest request) {
        PageDTO<Job> jobPageDTO = this.jobDAO.pageList(BeanMapperUtil.map(request, JobPageDTO.class));
        if (CollectionUtils.isEmpty(jobPageDTO.getList())) {
            return PageUtil.emptyList(ListJobVO.class);
        }

        // App list.
        List<Long> appIds = jobPageDTO.getList().stream()
                .map(Job::getAppId).distinct().collect(Collectors.toList());
        Map<Long, App> appMap = this.appDAO.getByIds(appIds).stream()
                .collect(Collectors.toMap(App::getId, a -> a));

        // Page
        return PageUtil.convert(jobPageDTO, j -> {
            ListJobVO listJobVO = BeanMapperUtil.map(j, ListJobVO.class);
            App app = appMap.get(j.getAppId());
            if (Objects.nonNull(app)) {
                listJobVO.setAppName(app.getName());
            }

            if (!TimeExpressionTypeEnum.isCron(j.getTimeExpressionType())) {
                listJobVO.setTimeExpressionValue(Long.valueOf(j.getTimeExpression()));
            }

            // Processor type
            if (ProcessorTypeEnum.isShell(j.getProcessorType())) {
                ShellProcessorDTO shellProcessorDTO = JsonUtil.decode(j.getProcessorInfo(), ShellProcessorDTO.class);
                listJobVO.setShellProcessorType(shellProcessorDTO.getType());
                listJobVO.setShellProcessorInfo(shellProcessorDTO.getContent());
            } else if (ProcessorTypeEnum.isKettle(j.getProcessorType())) {
                ShellProcessorDTO shellProcessorDTO = JsonUtil.decode(j.getProcessorInfo(), ShellProcessorDTO.class);
                listJobVO.setKettleProcessorType(shellProcessorDTO.getType());
                listJobVO.setKettleProcessorInfo(shellProcessorDTO.getContent());
            }

            // Execute type
            if (ExecuteTypeEnum.isSharding(j.getExecuteType())) {
                listJobVO.setShardingParams(j.getParams());
            }
            return listJobVO;
        });
    }

    /**
     * Valid job
     *
     * @param request request
     */
    private void validAndInitJob(AddJobRequest request) {
        if (ProcessorTypeEnum.isShell(request.getProcessorType())) {
            // Shell
            if (StringUtils.isBlank(request.getShellProcessorType())) {
                CodeEnum.SHELL_PROCESSOR_TYPE_INVALID.throwException();
            }

            if (StringUtils.isBlank(request.getShellProcessorInfo())) {
                CodeEnum.SHELL_PROCESSOR_INFO_INVALID.throwException();
            }

            ShellProcessorDTO shellProcessorDTO = new ShellProcessorDTO();
            shellProcessorDTO.setContent(request.getShellProcessorInfo());
            shellProcessorDTO.setType(request.getShellProcessorType());
            request.setProcessorInfo(JsonUtil.encode(shellProcessorDTO));
        } else if (ProcessorTypeEnum.isKettle(request.getProcessorType())) {
            // Kettle
            if (StringUtils.isBlank(request.getKettleProcessorType())) {
                CodeEnum.KETTLE_PROCESSOR_TYPE_INVALID.throwException();
            }

            if (StringUtils.isBlank(request.getKettleProcessorInfo())) {
                CodeEnum.KETTLE_PROCESSOR_INFO_INVALID.throwException();
            }

            ShellProcessorDTO shellProcessorDTO = new ShellProcessorDTO();
            shellProcessorDTO.setContent(request.getKettleProcessorInfo());
            shellProcessorDTO.setType(request.getKettleProcessorType());
            request.setProcessorInfo(JsonUtil.encode(shellProcessorDTO));
        } else if (ProcessorTypeEnum.isHttp(request.getProcessorType())) {
            AddJobRequest.HttpProcessorRequest httpProcessor = request.getHttpProcessor();
            if (ResponseModeEnum.isStatus(httpProcessor.getResponseMode())) {
                CodeEnum.HTTP_PROCESSOR_STATUS_V_INVALID.assertIsFalse(StringUtils.isEmpty(httpProcessor.getValue()));
            }

            if (ResponseModeEnum.isStatus(httpProcessor.getResponseMode())) {
                Boolean kvEmpty = StringUtils.isBlank(httpProcessor.getKey()) || StringUtils.isBlank(httpProcessor.getValue());
                CodeEnum.HTTP_PROCESSOR_JSON_KV_INVALID.assertIsFalse(kvEmpty);
            }

            if (ResponseModeEnum.isStatus(httpProcessor.getResponseMode())) {
                CodeEnum.HTTP_PROCESSOR_STRING_V_INVALID.assertIsFalse(StringUtils.isEmpty(httpProcessor.getValue()));
            }
            request.setProcessorInfo(JsonUtil.encode(httpProcessor));
        }

        // ShardingParams
        if (ExecuteTypeEnum.isSharding(request.getExecuteType())) {
            Boolean isInvalid = StringUtils.isBlank(request.getShardingParams()) || !request.getShardingParams().contains("=");
            CodeEnum.SHARDING_PARAMS_INVALID.assertIsFalse(isInvalid);

            request.setParams(request.getShardingParams());
        }

        // Cron
        if (TimeExpressionTypeEnum.isCron(request.getTimeExpressionType())) {
            long one = this.parseTimeExpression(request.getTimeExpression());
            long two = this.parseTimeExpression(request.getTimeExpression());
            if ((two - one) < TimeUnit.MINUTES.toSeconds(1)) {
                CodeEnum.JOB_CRON_INTERVAL_INVALID.throwException();
            }
        }
    }

    /**
     * Update job
     *
     * @param updateJob updateJob
     */
    private void updateJobBySecond(Job updateJob) {
        JobInstance jobInstance = this.jobInstanceDAO.getFirstByJobIdAndStatus(updateJob.getId(), InstanceStatusEnum.RUNNING.getStatus());
        Optional.ofNullable(jobInstance).ifPresent(ji -> {
            // Stop all running job instance
            JobInstanceStopRequestDTO stopRequest = new JobInstanceStopRequestDTO();
            stopRequest.setJobInstanceId(ji.getId());
            this.jobInstanceScheduler.stop(stopRequest);
        });

        // Job running status
        // Dispatch scheduler for new configuration
        if (JobStatusEnum.isRunning(updateJob.getStatus())) {
            this.createJobInstance(updateJob);
        }
    }

    /**
     * Create job instance
     *
     * @param job job
     */
    private void createJobInstance(Job job) {
        Long timestamp = DateUtil.timestamp();
        JobInstance jobInstance = BeanMapperUtil.map(job, JobInstance.class);
        jobInstance.setJobId(job.getId());
        jobInstance.setDeleteTime(0L);
        jobInstance.setDeleted(CommonConstant.NO);
        jobInstance.setStatus(InstanceStatusEnum.WAITING.getStatus());
        jobInstance.setFailStatus(FailStatusEnum.NONE.getStatus());
        jobInstance.setDispatchVersion(0L);
        jobInstance.setCompleteTime(0L);
        jobInstance.setLastReportTime(0L);
        jobInstance.setWorkerAddress("");
        jobInstance.setExecuteTime(job.getNextExecuteTime());
        jobInstance.setCreateTime(timestamp);
        jobInstance.setUpdateTime(timestamp);
        this.jobInstanceDAO.save(jobInstance);
    }

    /**
     * Parse time expression
     *
     * @param timeExpression timeExpression
     * @return Long
     */
    private Long parseTimeExpression(String timeExpression) {
        try {
            CronExpression cronExpression = new CronExpression(timeExpression);
            return cronExpression.getNextValidTimeAfter(new Date()).toInstant().getEpochSecond();
        } catch (ParseException e) {
            CodeEnum.TIME_EXPRESSION_INVALID.throwException();
            log.error("Parse time expression failed! timeExpression=" + timeExpression, e);
            return 0L;
        }
    }
}
