package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobNotifyTemplateDAO;
import io.openjob.server.repository.entity.JobNotifyTemplate;
import io.openjob.server.repository.repository.JobNotifyTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:44:57
 * @since 1.0.0
 */
@Component
public class JobNotifyTemplateDAOImpl implements JobNotifyTemplateDAO {
    private final JobNotifyTemplateRepository jobNotifyTemplateRepository;

    @Autowired
    public JobNotifyTemplateDAOImpl(JobNotifyTemplateRepository jobNotifyTemplateRepository) {
        this.jobNotifyTemplateRepository = jobNotifyTemplateRepository;
    }

    @Override
    public Long add(JobNotifyTemplate entity) {
        jobNotifyTemplateRepository.save(entity);

        return entity.getId();
    }

    @Override
    public void batchAdd(List<JobNotifyTemplate> entityList) {
        jobNotifyTemplateRepository.saveAll(entityList);
    }

    @Override
    public JobNotifyTemplate getById(Long id) {
        return jobNotifyTemplateRepository.getById(id);
    }

    @Override
    public Integer updateById(JobNotifyTemplate entity) {
        return jobNotifyTemplateRepository.updateById(entity);
    }
}

