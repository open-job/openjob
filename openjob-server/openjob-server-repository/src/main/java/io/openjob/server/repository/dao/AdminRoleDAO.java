package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminRole;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminRoleDAO {

    /**
     * add AdminRole
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminRole entity);

    /**
     * batch add AdminRole
     *
     * @param entityList entity list
     */
    void batchAdd(List<AdminRole> entityList);

    /**
     * get AdminRole by Id
     *
     * @param id id
     * @return AdminRole
     */
    AdminRole getById(Long id);

    /**
     * update AdminRole by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminRole entity);

    /**
     * get AdminRole list by IDs
     *
     * @param ids ids
     * @return AdminRole list
     */
    List<AdminRole> getByIds(List<Long> ids);

    /**
     * get AdminRole list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminRole list
     */
    Page<AdminRole> getPageList(Integer page, Integer size);
}

