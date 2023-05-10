package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import io.openjob.server.repository.entity.JobInstanceTask;
import io.openjob.server.repository.repository.JobInstanceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class JobInstanceTaskDAOImpl implements JobInstanceTaskDAO {
    private final JobInstanceTaskRepository jobInstanceTaskRepository;

    @Autowired
    public JobInstanceTaskDAOImpl(JobInstanceTaskRepository jobInstanceTaskRepository) {
        this.jobInstanceTaskRepository = jobInstanceTaskRepository;
    }

    @Override
    public Long save(JobInstanceTask jobInstanceTask) {
        return this.jobInstanceTaskRepository.save(jobInstanceTask).getId();
    }

    @Override
    public Integer batchSave(List<JobInstanceTask> taskList) {
        return this.jobInstanceTaskRepository.saveAll(taskList).size();
    }

    @Override
    public JobInstanceTask getByJobInstanceId(Long jobInstanceId) {
        return this.jobInstanceTaskRepository.findByJobInstanceId(jobInstanceId);
    }
}
