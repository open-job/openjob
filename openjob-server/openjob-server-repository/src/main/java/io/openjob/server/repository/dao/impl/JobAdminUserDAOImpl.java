package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobAdminUserDAO;
import io.openjob.server.repository.entity.JobAdminUser;
import io.openjob.server.repository.repository.JobAdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:33:40
 * @since 1.0.0
 */
@Component
public class JobAdminUserDAOImpl implements JobAdminUserDAO {
    private final JobAdminUserRepository jobAdminUserRepository;

    @Autowired
    public JobAdminUserDAOImpl(JobAdminUserRepository jobAdminUserRepository) {
        this.jobAdminUserRepository = jobAdminUserRepository;
    }

    @Override
    public Long add(JobAdminUser entity) {
        jobAdminUserRepository.save(entity);

        return entity.getId();
    }

    @Override
    public void batchAdd(List<JobAdminUser> entityList) {
        jobAdminUserRepository.saveAll(entityList);
    }

    @Override
    public JobAdminUser getById(Long id) {
        return jobAdminUserRepository.getById(id);
    }

    @Override
    public Integer updateById(JobAdminUser entity) {
        return jobAdminUserRepository.updateById(entity);
    }
}

