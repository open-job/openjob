package io.openjob.server.admin.service.impl;

import com.google.common.collect.Lists;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.common.constant.ProcessorTypeEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.dto.ShellProcessorDTO;
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
import io.openjob.server.scheduler.dto.JobExecuteRequestDTO;
import io.openjob.server.scheduler.service.JobSchedulingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private final JobSchedulingService jobSchedulingService;

    @Autowired
    public JobServiceImpl(JobDAO jobDAO, AppDAO appDAO, JobInstanceDAO jobInstanceDAO, JobSchedulingService jobSchedulingService) {
        this.jobDAO = jobDAO;
        this.appDAO = appDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.jobSchedulingService = jobSchedulingService;
    }

    @Override
    public AddJobVO add(AddJobRequest addJobRequest) {
        // Pre handle request
        this.preHandleJob(addJobRequest);

        if (!TimeExpressionTypeEnum.isCron(addJobRequest.getTimeExpressionType())) {
            addJobRequest.setTimeExpression(String.valueOf(addJobRequest.getTimeExpressionValue()));
        }

        // Running status to parse next execute time.
        Job job = BeanMapperUtil.map(addJobRequest, Job.class);
        if (TimeExpressionTypeEnum.isCron(addJobRequest.getTimeExpressionType()) && JobStatusEnum.isRunning(job.getStatus())) {
            job.setNextExecuteTime(this.parseTimeExpression(job.getTimeExpression()));
        } else if (TimeExpressionTypeEnum.isOneTime(addJobRequest.getTimeExpressionType())) {
            job.setNextExecuteTime(Long.valueOf(addJobRequest.getTimeExpression()));
        }

        long id = this.jobDAO.save(job);
        return new AddJobVO().setId(id);
    }

    @Override
    public UpdateJobVO update(UpdateJobRequest updateJobRequest) {
        // Pre handle job
        this.preHandleJob(updateJobRequest);

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

        this.jobDAO.update(updateJob);
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
    public UpdateJobStatusVO updateStatus(UpdateJobStatusRequest request) {
        Long nextExecuteTime = null;
        Job originJob = this.jobDAO.getById(request.getId());
        if (TimeExpressionTypeEnum.isCron(originJob.getTimeExpressionType()) && JobStatusEnum.isRunning(request.getStatus())) {
            nextExecuteTime = this.parseTimeExpression(originJob.getTimeExpression());
        }

        this.jobDAO.updateByStatusOrDeleted(request.getId(), request.getStatus(), null, nextExecuteTime);
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
     * Check job
     *
     * @param request request
     */
    private void preHandleJob(AddJobRequest request) {
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
        }

        // ShardingParams
        if (ExecuteTypeEnum.isSharding(request.getExecuteType())) {
            if (StringUtils.isBlank(request.getShardingParams())) {
                CodeEnum.SHARDING_PARAMS_INVALID.throwException();
            }

            request.setParams(request.getShardingParams());
        }
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
            return null;
        }
    }
}
