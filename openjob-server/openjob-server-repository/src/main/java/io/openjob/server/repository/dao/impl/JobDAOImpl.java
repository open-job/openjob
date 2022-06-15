package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobDAOImpl implements JobDAO {
    private final JobRepository jobRepository;

    @Autowired
    public JobDAOImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Long save(Job job) {
        return jobRepository.save(job).getId();
    }
}
