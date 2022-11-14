package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.NotifyTemplate;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:33:23
 * @since 1.0.0
 */
public interface NotifyTemplateDAO {

    /**
     * add NotifyTemplate
     *
     * @param entity entity
     * @return id
     */
    Long add(NotifyTemplate entity);

    /**
     * batch add NotifyTemplate
     *
     * @param entityList entity list
     */
    void batchAdd(List<NotifyTemplate> entityList);

    /**
     * get NotifyTemplate by Id
     *
     * @param id id
     * @return NotifyTemplate
     */
    NotifyTemplate getById(Long id);

    /**
     * update NotifyTemplate by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(NotifyTemplate entity);

}

