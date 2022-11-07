package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobAdminRuleDAO;
import io.openjob.server.repository.entity.JobAdminRule;
import io.openjob.server.repository.repository.JobAdminRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:43:06
 * @since 1.0.0
 */
@Component
public class JobAdminRuleDAOImpl implements JobAdminRuleDAO {
    private final JobAdminRuleRepository jobAdminRuleRepository;

    @Autowired
    public JobAdminRuleDAOImpl(JobAdminRuleRepository jobAdminRuleRepository) {
        this.jobAdminRuleRepository = jobAdminRuleRepository;
    }

    @Override
    public Long add(JobAdminRule entity) {
        jobAdminRuleRepository.save(entity);

        return entity.getId();
    }

    @Override
    public void batchAdd(List<JobAdminRule> entityList) {
        jobAdminRuleRepository.saveAll(entityList);
    }

    @Override
    public JobAdminRule getById(Long id) {
        return jobAdminRuleRepository.getById(id);
    }

    @Override
    public Integer updateById(JobAdminRule entity) {
        return jobAdminRuleRepository.updateById(entity);
    }
}

