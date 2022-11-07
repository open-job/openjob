package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobNotifyContactDAO;
import io.openjob.server.repository.entity.JobNotifyContact;
import io.openjob.server.repository.repository.JobNotifyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:45:10
 * @since 1.0.0
 */
@Component
public class JobNotifyContactDAOImpl implements JobNotifyContactDAO {
    private final JobNotifyContactRepository jobNotifyContactRepository;

    @Autowired
    public JobNotifyContactDAOImpl(JobNotifyContactRepository jobNotifyContactRepository) {
        this.jobNotifyContactRepository = jobNotifyContactRepository;
    }

    @Override
    public Long add(JobNotifyContact entity) {
        jobNotifyContactRepository.save(entity);

        return entity.getId();
    }

    @Override
    public void batchAdd(List<JobNotifyContact> entityList) {
        jobNotifyContactRepository.saveAll(entityList);
    }

    @Override
    public JobNotifyContact getById(Long id) {
        return jobNotifyContactRepository.getById(id);
    }

    @Override
    public Integer updateById(JobNotifyContact entity) {
        return jobNotifyContactRepository.updateById(entity);
    }
}

