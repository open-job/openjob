package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobAdminConfig;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:33:03
 * @since 1.0.0
 */
public interface JobAdminConfigDAO {

    /**
     * add JobAdminConfig
     *
     * @param entity entity
     * @return id
     */
    Long add(JobAdminConfig entity);

    /**
     * batch add JobAdminConfig
     *
     * @param entityList entity list
     * @return number
     */
    Integer batchAdd(List<JobAdminConfig> entityList);

    /**
     * get JobAdminConfig by Id
     *
     * @param id id
     * @return JobAdminConfig
     */
    JobAdminConfig getById(Long id);

    /**
     * update JobAdminConfig by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(JobAdminConfig entity);

}

