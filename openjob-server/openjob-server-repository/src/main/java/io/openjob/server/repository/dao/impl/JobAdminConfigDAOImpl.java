package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobAdminConfigDAO;
import io.openjob.server.repository.entity.JobAdminConfig;
import io.openjob.server.repository.mapper.JobAdminConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:33:05
 * @since 1.0.0
 */
@Component
public class JobAdminConfigDAOImpl implements JobAdminConfigDAO {
    private final JobAdminConfigMapper jobAdminConfigMapper;

    @Autowired
    public JobAdminConfigDAOImpl(JobAdminConfigMapper jobAdminConfigMapper) {
        this.jobAdminConfigMapper = jobAdminConfigMapper;
    }

    @Override
    public Long add(JobAdminConfig entity) {
        jobAdminConfigMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public Integer batchAdd(List<JobAdminConfig> entityList) {
        return jobAdminConfigMapper.saveBatch(entityList, entityList.size());
    }

    @Override
    public JobAdminConfig getById(Long id) {
        return jobAdminConfigMapper.getById(id);
    }

    @Override
    public Integer updateById(JobAdminConfig entity) {
        return jobAdminConfigMapper.updateById(entity);
    }
}

