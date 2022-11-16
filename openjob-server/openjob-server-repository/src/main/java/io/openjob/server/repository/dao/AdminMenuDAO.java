package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminMenu;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:58
 * @since 1.0.0
 */
public interface AdminMenuDAO {

    /**
     * add AdminMenu
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminMenu entity);

    /**
     * batch add AdminMenu
     *
     * @param entityList entity list
     */
    void batchAdd(List<AdminMenu> entityList);

    /**
     * get AdminMenu by Id
     *
     * @param id id
     * @return AdminMenu
     */
    AdminMenu getById(Long id);

    /**
     * update AdminMenu by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminMenu entity);

}

