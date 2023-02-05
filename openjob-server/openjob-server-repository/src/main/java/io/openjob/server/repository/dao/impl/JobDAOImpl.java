package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.server.repository.constant.JobStatusEnum;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.repository.JobRepository;
import io.openjob.server.repository.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<Job> listScheduledJobs(List<Long> slotIds, Long time) {
        List<String> notTypes = Arrays.asList(TimeExpressionTypeEnum.NONE.getType(), TimeExpressionTypeEnum.SECOND_DELAY.getType());
        return jobRepository.findBySlotsIdInAndStatusAndTimeExpressionTypeNotInAndNextExecuteTimeLessThanEqual(slotIds, JobStatusEnum.RUNNING.getStatus(), notTypes, time);
    }

    @Override
    public List<Job> listScheduledSecondJobs(List<Long> slotIds) {
        return null;
    }

    @Override
    public Page<Job> list(Integer page, Integer size) {
        // TIP: page start from 0 on JPA.
        PageRequest pageReq = PageRequest.of(page - 1, size, EntityUtil.DEFAULT_SORT);

        return jobRepository.findAll(pageReq);
    }
}
