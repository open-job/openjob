package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminUser;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:35:45
 * @since 1.0.0
 */
public interface AdminUserDAO {

    /**
     * add AdminUser
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminUser entity);

    /**
     * batch add AdminUser
     *
     * @param entityList entity list
     * @return number
     */
    void batchAdd(List<AdminUser> entityList);

    /**
     * get AdminUser by Id
     *
     * @param id id
     * @return AdminUser
     */
    AdminUser getById(Long id);

    /**
     * update AdminUser by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminUser entity);

}
