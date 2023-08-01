package io.openjob.server.admin.service.impl;

import com.google.common.collect.Lists;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.common.constant.FailStatusEnum;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.server.admin.request.job.DeleteJobInstanceRequest;
import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.request.job.ListProcessorLogRequest;
import io.openjob.server.admin.request.job.StopJobInstanceRequest;
import io.openjob.server.admin.service.JobInstanceService;
import io.openjob.server.admin.util.LogFormatUtil;
import io.openjob.server.admin.vo.job.DeleteJobInstanceVO;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.admin.vo.job.ListProcessorLogVO;
import io.openjob.server.admin.vo.job.StopJobInstanceVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.JobInstanceLogDAO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.entity.JobInstanceLog;
import io.openjob.server.scheduler.dto.JobInstanceStopRequestDTO;
import io.openjob.server.scheduler.dto.JobInstanceStopResponseDTO;
import io.openjob.server.scheduler.scheduler.JobInstanceScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Service
public class JobInstanceServiceImpl implements JobInstanceService {
    private final LogDAO logDAO;
    private final JobDAO jobDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final JobInstanceLogDAO jobInstanceLogDAO;
    private final JobInstanceScheduler jobInstanceScheduler;

    @Autowired
    public JobInstanceServiceImpl(JobInstanceDAO jobInstanceDAO, LogDAO logDAO, JobInstanceLogDAO jobInstanceLogDAO, JobDAO jobDAO, JobInstanceScheduler jobInstanceScheduler) {
        this.logDAO = logDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.jobInstanceLogDAO = jobInstanceLogDAO;
        this.jobDAO = jobDAO;
        this.jobInstanceScheduler = jobInstanceScheduler;
    }

    @Override
    public PageVO<ListJobInstanceVO> getPageList(ListJobInstanceRequest request) {
        PageDTO<JobInstance> pageDTO = this.jobInstanceDAO.pageList(BeanMapperUtil.map(request, JobInstancePageDTO.class));

        // Empty
        if (CollectionUtils.isEmpty(pageDTO.getList())) {
            return PageUtil.empty(pageDTO);
        }

        // Job map
        List<Long> jobIds = pageDTO.getList().stream().map(JobInstance::getJobId).distinct().collect(Collectors.toList());
        Map<Long, Job> jobMap = this.jobDAO.getByIds(jobIds).stream().collect(Collectors.toMap(Job::getId, j -> j));
        return PageUtil.convert(pageDTO, n -> {
            ListJobInstanceVO listJobInstanceVO = BeanMapperUtil.map(n, ListJobInstanceVO.class);
            Job job = jobMap.get(n.getJobId());
            if (Objects.nonNull(job)) {
                listJobInstanceVO.setJobName(job.getName());
            }
            return listJobInstanceVO;
        });
    }

    @Override
    public StopJobInstanceVO stopInstance(StopJobInstanceRequest killRequest) {
        JobInstanceStopRequestDTO jobInstanceStopRequestDTO = new JobInstanceStopRequestDTO();
        jobInstanceStopRequestDTO.setJobInstanceId(killRequest.getId());
        JobInstanceStopResponseDTO stop = this.jobInstanceScheduler.stop(jobInstanceStopRequestDTO);

        // Stop success to update status.
        if (CommonConstant.INT_ZERO.equals(stop.getType())) {
            this.jobInstanceDAO.updateStatusById(killRequest.getId(), InstanceStatusEnum.STOP.getStatus(), FailStatusEnum.NONE.getStatus());
        }

        // Response
        StopJobInstanceVO stopJobInstanceVO = new StopJobInstanceVO();
        stopJobInstanceVO.setType(stop.getType());
        return stopJobInstanceVO;
    }

    @Override
    public DeleteJobInstanceVO deleteInstance(DeleteJobInstanceRequest request) {
        this.jobInstanceDAO.deleteById(request.getId());
        return new DeleteJobInstanceVO();
    }

    @Override
    public ListProcessorLogVO getProcessorList(ListProcessorLogRequest request) {
        AtomicLong nextTime = new AtomicLong(0L);
        List<String> list = Lists.newArrayList();

        // First to query job instance log.
        if (request.getTime().equals(0L) && ExecuteTypeEnum.isStandalone(request.getExecuteType())) {
            List<JobInstanceLog> jobInstanceLogs = this.jobInstanceLogDAO.getByJobInstanceId(request.getJobInstanceId());
            jobInstanceLogs.forEach(j -> {
                list.add(this.formatLogInstanceLog(j));
                nextTime.set(j.getCreateTime() * 1000);
            });
        }

        Integer isComplete = CommonConstant.NO;
        try {
            String taskId = TaskUtil.getRandomUniqueId(request.getJobId(), request.getJobInstanceId(), 0L, 0L);
            List<ProcessorLogDTO> processorLogs = this.logDAO.queryByScroll(taskId, request.getTime(), request.getSize());

            if (!CollectionUtils.isEmpty(processorLogs)) {
                // Processor list and nextTime.
                processorLogs.forEach(l -> list.add(LogFormatUtil.formatLog(l)));
                nextTime.set(processorLogs.get(processorLogs.size() - 1).getTime());
            } else {
                boolean completeStatus = !InstanceStatusEnum.NOT_COMPLETE.contains(request.getStatus());
                if (completeStatus) {
                    isComplete = CommonConstant.YES;
                } else if (CommonConstant.YES.equals(request.getLoading())) {
                    JobInstance queryInstance = this.jobInstanceDAO.getById(request.getJobInstanceId());
                    if (!InstanceStatusEnum.NOT_COMPLETE.contains(queryInstance.getStatus())) {
                        isComplete = CommonConstant.YES;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ListProcessorLogVO listProcessorLogVO = new ListProcessorLogVO();
        listProcessorLogVO.setList(list);
        listProcessorLogVO.setTime(nextTime.get());
        listProcessorLogVO.setComplete(isComplete);
        return listProcessorLogVO;
    }

    private String formatLogInstanceLog(JobInstanceLog jobInstanceLog) {
        return String.format(
                LogFormatUtil.LOG_FORMAT,
                DateUtil.formatTimestamp(jobInstanceLog.getCreateTime() * 1000),
                "WARNING",
                "",
                jobInstanceLog.getMessage()
        );
    }
}
