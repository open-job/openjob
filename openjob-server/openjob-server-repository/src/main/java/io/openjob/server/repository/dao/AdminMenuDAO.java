package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminMenu;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
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

    /**
     * get AdminMenu list by ID
     *
     * @param ids ids
     * @return AdminMenu list
     */
    List<AdminMenu> getByIds(List<Long> ids);

    /**
     * get AdminMenu list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminMenu list
     */
    Page<AdminMenu> getPageList(Integer page, Integer size);

    /**
     * get all AdminMenu list
     *
     * @return list
     */
    List<AdminMenu> getAllMenus();
}

