package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.NotifyContact;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:41
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

