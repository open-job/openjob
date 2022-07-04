package io.openjob.server.cluster.service;

import io.openjob.server.repository.dao.JobDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class JobSchedulerService {
    private final JobDAO jobDAO;

    @Autowired
    public JobSchedulerService(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    public void scheduleJob() {
        System.out.println(jobDAO);
    }
}
