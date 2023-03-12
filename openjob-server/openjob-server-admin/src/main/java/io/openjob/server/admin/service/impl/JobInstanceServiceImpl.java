package io.openjob.server.admin.service.impl;

import com.google.common.collect.Lists;
import io.openjob.common.constant.ExecuteTypeEnum;
import io.openjob.common.constant.LogFieldConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.admin.request.job.KillJobInstanceRequest;
import io.openjob.server.admin.request.job.ListJobInstanceRequest;
import io.openjob.server.admin.request.job.ListProcessorLogRequest;
import io.openjob.server.admin.service.JobInstanceService;
import io.openjob.server.admin.util.LogFormatUtil;
import io.openjob.server.admin.vo.job.KillJobInstanceVO;
import io.openjob.server.admin.vo.job.ListJobInstanceVO;
import io.openjob.server.admin.vo.job.ListProcessorLogVO;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.PageUtil;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLog;
import io.openjob.server.log.dto.ProcessorLogField;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.JobInstanceLogDAO;
import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.entity.JobInstanceLog;
import io.openjob.server.repository.entity.JobInstanceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class JobInstanceServiceImpl implements JobInstanceService {
    private final static String LOG_FORMAT = "%-23s %-7s %-40s : %s";
    private final LogDAO logDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final JobInstanceLogDAO jobInstanceLogDAO;
    private final JobInstanceTaskDAO jobInstanceTaskDAO;


    @Autowired
    public JobInstanceServiceImpl(JobInstanceDAO jobInstanceDAO, LogDAO logDAO, JobInstanceLogDAO jobInstanceLogDAO, JobInstanceTaskDAO jobInstanceTaskDAO) {
        this.logDAO = logDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.jobInstanceLogDAO = jobInstanceLogDAO;
        this.jobInstanceTaskDAO = jobInstanceTaskDAO;
    }

    @Override
    public PageVO<ListJobInstanceVO> getPageList(ListJobInstanceRequest request) {
        PageDTO<JobInstance> pageDTO = this.jobInstanceDAO.pageList(BeanMapperUtil.map(request, JobInstancePageDTO.class));
        return PageUtil.convert(pageDTO, n -> BeanMapperUtil.map(n, ListJobInstanceVO.class));
    }

    @Override
    public KillJobInstanceVO killInstance(KillJobInstanceRequest killRequest) {
        return new KillJobInstanceVO();
    }

    @Override
    public ListProcessorLogVO getProcessorList(ListProcessorLogRequest request) {
        Long nextTime = 0L;
        List<String> list = Lists.newArrayList();
        if (ExecuteTypeEnum.isStandalone(request.getExecuteType())) {
            JobInstanceLog jobInstanceLog = this.jobInstanceLogDAO.getByJobInstanceId(request.getJobInstanceId());
            list.add(this.formatLogInstanceLog(jobInstanceLog));
        }

        JobInstanceTask jobInstanceTask = this.jobInstanceTaskDAO.getByJobInstanceId(request.getJobInstanceId());
        if (Objects.nonNull(jobInstanceTask)) {
            try {
                List<ProcessorLog> processorLogs = this.logDAO.queryByPage(jobInstanceTask.getTaskId(), request.getTime(), request.getSize());
                processorLogs.forEach(l -> list.add(this.formatLog(l)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        ListProcessorLogVO listProcessorLogVO = new ListProcessorLogVO();
        listProcessorLogVO.setList(list);
        listProcessorLogVO.setTime(nextTime);
        return listProcessorLogVO;
    }

    private String formatLogInstanceLog(JobInstanceLog jobInstanceLog) {
        return String.format(
                LOG_FORMAT,
                DateUtil.formatTimestamp(jobInstanceLog.getCreateTime() * 1000),
                "WARNING",
                "",
                jobInstanceLog.getMessage()
        );
    }

    private String formatLog(ProcessorLog processorLog) {
        Map<String, String> fieldMap = processorLog.getFields().stream()
                .collect(Collectors.toMap(ProcessorLogField::getName, ProcessorLogField::getValue));

        String location = fieldMap.get(LogFieldConstant.LOCATION);
        return String.format(
                LOG_FORMAT,
                DateUtil.formatTimestamp(Long.parseLong(fieldMap.get(LogFieldConstant.TIME_STAMP))),
                fieldMap.get(LogFieldConstant.LEVEL),
                LogFormatUtil.formatLocation(location),
                fieldMap.get(LogFieldConstant.MESSAGE)
        );
    }
}
