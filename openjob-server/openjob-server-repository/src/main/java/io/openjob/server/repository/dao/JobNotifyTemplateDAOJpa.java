package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobNotifyTemplate;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:44:57
 * @since 1.0.0
 */
public interface JobNotifyTemplateDAO {

    /**
     * add JobNotifyTemplate
     *
     * @param entity entity
     * @return id
     */
    Long add(JobNotifyTemplate entity);

    /**
     * batch add JobNotifyTemplate
     *
     * @param entityList entity list
     * @return number
     */
    void batchAdd(List<JobNotifyTemplate> entityList);

    /**
     * get JobNotifyTemplate by Id
     *
     * @param id id
     * @return JobNotifyTemplate
     */
    JobNotifyTemplate getById(Long id);

    /**
     * update JobNotifyTemplate by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(JobNotifyTemplate entity);

}

