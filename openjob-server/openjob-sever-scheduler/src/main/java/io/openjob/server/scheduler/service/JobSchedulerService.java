package io.openjob.server.scheduler.service;

import io.openjob.server.common.ClusterContext;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.cron.CronExpression;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.scheduler.Scheduler;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.timer.TimerTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
@Log4j2
public class JobSchedulerService {
    private final JobDAO jobDAO;
    private final JobInstanceDAO jobInstanceDAO;

    @Autowired
    public JobSchedulerService(JobDAO jobDAO, JobInstanceDAO jobInstanceDAO) {
        this.jobDAO = jobDAO;
        this.jobInstanceDAO = jobInstanceDAO;
    }

    /**
     * Schedule job.
     */
    public void scheduleJob() {
        List<Long> currentSlots = new ArrayList<>(ClusterContext.getCurrentSlots());
        // Cron jobs.
        this.scheduleCronJob(currentSlots);
    }

    /**
     * Schedule cron job.
     *
     * @param currentSlots currentSlots
     */
    @Transactional(rollbackFor = Exception.class)
    public void scheduleCronJob(List<Long> currentSlots) {
        Integer maxExecuteTime = DateUtil.now() + (int) (SchedulerConstant.JOB_FIXED_DELAY / 1000L);
        List<Job> jobs = jobDAO.listScheduledJobs(currentSlots, maxExecuteTime);

        List<TimerTask> timerTasks = new ArrayList<>();

        // Create job instance.
        jobs.forEach(j -> {
            JobInstance jobInstance = new JobInstance();
            Long instanceId = jobInstanceDAO.save(jobInstance);
            TimerTask timerTask = new TimerTask(instanceId, j.getSlotsId(), (long) j.getNextExecuteTime() - DateUtil.now());
            timerTasks.add(timerTask);
        });

        // Update job next execute time.
        jobs.forEach(j -> {
            try {
                // Calculate next execute time.
                Integer nextExecuteTime = this.calculateNextExecuteTime(j);

                // Update next execute time.
                Job job = new Job();
                job.setId(j.getId());
                job.setNextExecuteTime(nextExecuteTime);
                job.setUpdateTime(DateUtil.now());
                jobDAO.save(job);
            } catch (ParseException parseException) {
                log.error("Cron expression({}) is invalid!", j.getTimeExpression());
            }

            Scheduler.addTimerTask(timerTasks);
        });
    }

    private Integer calculateNextExecuteTime(Job job) throws ParseException {
        // Cron type job.
        if (TimeExpressionTypeEnum.CRON_TYPES.contains(job.getTimeExpressionType())) {
            CronExpression cronExpression = new CronExpression(job.getTimeExpression());
            return (int) cronExpression.getNextValidTimeAfter(new Date()).toInstant().toEpochMilli();
        }

        // Fixed rate job.
        return job.getNextExecuteTime() + Integer.parseInt(job.getTimeExpression());
    }
}
