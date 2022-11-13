package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminConfig;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:35:55
 * @since 1.0.0
 */
public interface AdminConfigDAO {

    /**
     * add AdminConfig
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminConfig entity);

    /**
     * batch add AdminConfig
     *
     * @param entityList entity list
     */
    void batchAdd(List<AdminConfig> entityList);

    /**
     * get AdminConfig by Id
     *
     * @param id id
     * @return AdminConfig
     */
    AdminConfig getById(Long id);

    /**
     * update AdminConfig by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminConfig entity);

}

