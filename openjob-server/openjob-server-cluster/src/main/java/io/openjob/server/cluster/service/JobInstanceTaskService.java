package io.openjob.server.cluster.service;

import io.openjob.server.repository.dao.JobInstanceTaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class JobInstanceTaskService {
    private final JobInstanceTaskDAO jobInstanceTaskDAO;

    @Autowired
    public JobInstanceTaskService(JobInstanceTaskDAO jobInstanceTaskDAO) {
        this.jobInstanceTaskDAO = jobInstanceTaskDAO;
    }
}
