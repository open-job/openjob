package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.JobContactGroupDAO;
import io.openjob.server.repository.entity.JobContactGroup;
import io.openjob.server.repository.repository.JobContactGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:45:22
 * @since 1.0.0
 */
@Component
public class JobContactGroupDAOImpl implements JobContactGroupDAO {
    private final JobContactGroupRepository jobContactGroupRepository;

    @Autowired
    public JobContactGroupDAOImpl(JobContactGroupRepository jobContactGroupRepository) {
        this.jobContactGroupRepository = jobContactGroupRepository;
    }

    @Override
    public Long add(JobContactGroup entity) {
        jobContactGroupRepository.save(entity);

        return entity.getId();
    }

    @Override
    public void batchAdd(List<JobContactGroup> entityList) {
        jobContactGroupRepository.saveAll(entityList);
    }

    @Override
    public JobContactGroup getById(Long id) {
        return jobContactGroupRepository.getById(id);
    }

    @Override
    public Integer updateById(JobContactGroup entity) {
        return jobContactGroupRepository.updateById(entity);
    }
}

