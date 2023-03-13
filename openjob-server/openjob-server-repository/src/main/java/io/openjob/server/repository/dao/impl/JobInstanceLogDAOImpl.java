package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobInstanceLogDAO;
import io.openjob.server.repository.entity.JobInstanceLog;
import io.openjob.server.repository.repository.JobInstanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobInstanceLogDAOImpl implements JobInstanceLogDAO {
    private final JobInstanceLogRepository jobInstanceLogRepository;

    @Autowired
    public JobInstanceLogDAOImpl(JobInstanceLogRepository jobInstanceLogRepository) {
        this.jobInstanceLogRepository = jobInstanceLogRepository;
    }

    @Override
    public Long save(JobInstanceLog jobInstanceLog) {
        return this.jobInstanceLogRepository.save(jobInstanceLog).getId();
    }

    @Override
    public List<JobInstanceLog> getByJobInstanceId(Long jobInstanceId) {
        return this.jobInstanceLogRepository.findByJobInstanceIdOrderByCreateTimeAsc(jobInstanceId);
    }
}
