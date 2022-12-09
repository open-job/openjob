package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.repository.JobInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public Integer updateStatusAndCompleteTimeById(Long id, Integer status) {
        long now = DateUtil.timestamp();
        return this.jobInstanceRepository.update(id, status, now, now);
    }

    @Override
    public Integer updateLastReportTimeById(Long id, Long lastReportTime) {
        return this.jobInstanceRepository.update(id, lastReportTime, DateUtil.timestamp());
    }
}
