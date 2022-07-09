package io.openjob.server.scheduler.service;

import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.common.SpringContext;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class JobSchedulerService {
    private final JobDAO jobDAO;
    private final JobInstanceDAO jobInstanceDAO;

    @Autowired
    public JobSchedulerService(JobDAO jobDAO, JobInstanceDAO jobInstanceDAO) {
        this.jobDAO = jobDAO;
        this.jobInstanceDAO = jobInstanceDAO;
    }

    public void scheduleJob() {
        List<Long> currentSlots = new ArrayList<>(ClusterStatus.getCurrentSlots());
        // Cron jobs.
        this.scheduleCronJob(currentSlots);
    }

    @Transactional(rollbackFor = Exception.class)
    public void scheduleCronJob(List<Long> currentSlots) {
        Integer maxExecuteTime = DateUtil.now() + (int) (SchedulerConstant.JOB_FIXED_DELAY / 1000L);
        List<Job> jobs = jobDAO.listScheduledJobs(currentSlots, maxExecuteTime);

        // Create job instance.
        jobs.forEach(j -> {
            JobInstance jobInstance = new JobInstance();
            jobInstanceDAO.save(jobInstance);
        });

        // Update job next execute time.
        jobs.forEach(j -> {
            // Calculate next execute time.

            // Update next execute time.
        });

        // Push job instance to wheel.
    }
}
