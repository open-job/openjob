package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.NotifyContact;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface NotifyContactDAO {

    /**
     * add NotifyContact
     *
     * @param entity entity
     * @return id
     */
    Long add(NotifyContact entity);

    /**
     * batch add NotifyContact
     *
     * @param entityList entity list
     */
    void batchAdd(List<NotifyContact> entityList);

    /**
     * get NotifyContact by Id
     *
     * @param id id
     * @return NotifyContact
     */
    NotifyContact getById(Long id);

    /**
     * update NotifyContact by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(NotifyContact entity);

}

