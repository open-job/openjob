package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobContactGroup;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:45:22
 * @since 1.0.0
 */
public interface JobContactGroupDAO {

    /**
     * add JobContactGroup
     *
     * @param entity entity
     * @return id
     */
    Long add(JobContactGroup entity);

    /**
     * batch add JobContactGroup
     *
     * @param entityList entity list
     * @return number
     */
    void batchAdd(List<JobContactGroup> entityList);

    /**
     * get JobContactGroup by Id
     *
     * @param id id
     * @return JobContactGroup
     */
    JobContactGroup getById(Long id);

    /**
     * update JobContactGroup by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(JobContactGroup entity);

}

