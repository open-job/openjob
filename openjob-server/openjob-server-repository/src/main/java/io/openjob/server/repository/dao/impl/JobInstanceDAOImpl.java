package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.repository.JobInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public List<JobInstance> getFailoverList(Long lastReportTime, List<Integer> statusList) {
        return this.jobInstanceRepository.findByLastReportTimeLessThanAndStatusIn(lastReportTime, statusList);
    }

    @Override
    public Integer updateByRunning(Long id, String workerAddress, InstanceStatusEnum instance, Long lastReportTime) {
        return this.jobInstanceRepository.updateByRunning(id, workerAddress, instance.getStatus(), lastReportTime);
    }

    @Override
    public JobInstance getOneByIdAndStatus(Long id, Integer status) {
        return this.jobInstanceRepository.findFirstByIdAndStatus(id, status);
    }
}
