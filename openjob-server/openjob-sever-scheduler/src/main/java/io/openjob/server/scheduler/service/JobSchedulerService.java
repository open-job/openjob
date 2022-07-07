package io.openjob.server.scheduler.service;

import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.repository.dao.JobDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        List<Long> currentSlots = new ArrayList<>(ClusterStatus.getCurrentSlots());
        // Cron jobs.
        this.scheduleCronJob(currentSlots);

        // Second jobs.

        // Delay jobs.
    }

    public void scheduleCronJob(List<Long> currentSlots) {
//        jobDAO.listScheduledCronJobs(currentSlots, );
    }
}
