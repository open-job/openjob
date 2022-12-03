package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminUser;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
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

    /**
     * get AdminUser by username
     *
     * @param username username
     * @return AdminUser
     */
    AdminUser getByUsername(String username);

    /**
     * get AdminUser list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminUser list
     */
    Page<AdminUser> getPageList(Integer page, Integer size);
}

