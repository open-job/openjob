package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.JobNotifyContact;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:45:10
 * @since 1.0.0
 */
public interface JobNotifyContactDAO {

    /**
     * add JobNotifyContact
     *
     * @param entity entity
     * @return id
     */
    Long add(JobNotifyContact entity);

    /**
     * batch add JobNotifyContact
     *
     * @param entityList entity list
     * @return number
     */
    void batchAdd(List<JobNotifyContact> entityList);

    /**
     * get JobNotifyContact by Id
     *
     * @param id id
     * @return JobNotifyContact
     */
    JobNotifyContact getById(Long id);

    /**
     * update JobNotifyContact by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(JobNotifyContact entity);

}

