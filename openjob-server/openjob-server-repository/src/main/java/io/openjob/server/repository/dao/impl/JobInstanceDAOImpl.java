package io.openjob.server.repository.dao.impl;

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
}
