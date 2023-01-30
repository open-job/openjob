package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.repository.JobInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobInstanceDAOImpl implements JobInstanceDAO {
    private final JobInstanceRepository jobInstanceRepository;

    @Autowired
    public JobInstanceDAOImpl(JobInstanceRepository jobInstanceRepository) {
        this.jobInstanceRepository = jobInstanceRepository;
    }

    @Override
    public Long save(JobInstance jobInstance) {
        return this.jobInstanceRepository.save(jobInstance).getId();
    }

    @Override
    public Integer updateStatusById(Long id, Integer status) {
        long now = DateUtil.timestamp();
        return this.jobInstanceRepository.update(id, status, now, now);
    }

    @Override
    public Integer updateLastReportTimeByIds(List<Long> ids, Long lastReportTime) {
        return this.jobInstanceRepository.updateLastReportTimeByIds(ids, lastReportTime);
    }

    @Override
    public List<JobInstance> getUnDispatchedList(Set<Long> slotsIds, Long executeTime, InstanceStatusEnum status) {
        return this.jobInstanceRepository.findByExecuteTimeLessThanAndSlotsIdInAndStatus(executeTime, slotsIds, status.getStatus());
    }

    @Override
    public List<JobInstance> getFailoverList(Set<Long> slotsIds, Long lastReportTime, InstanceStatusEnum statusEnum) {
        return this.jobInstanceRepository.findByLastReportTimeLessThanAndSlotsIdInAndStatusAndTimeExpressionTypeNot(lastReportTime, slotsIds, statusEnum.getStatus(), TimeExpressionTypeEnum.ONE_TIME.name());
    }

    @Override
    public Integer updateByRunning(Long id, String workerAddress, InstanceStatusEnum instance, Long lastReportTime) {
        return this.jobInstanceRepository.updateByRunning(id, workerAddress, instance.getStatus(), lastReportTime);
    }

    @Override
    public JobInstance getOneByJobIdAndStatus(Long jobId, Long id, Integer status) {
        return this.jobInstanceRepository.findFirstByJobIdAndIdNotAndStatus(jobId, id, status);
    }
}
