package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminSession;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
 * @date 2023-02-08 14:21:17
 * @since 1.0.0
 */
public interface AdminSessionDAO {

    /**
     * add AdminSession
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminSession entity);

    /**
     * batch add AdminSession
     *
     * @param entityList entity list
     */
    void batchAdd(List<AdminSession> entityList);

    /**
     * get AdminSession by Id
     *
     * @param id id
     * @return AdminSession
     */
    AdminSession getById(Long id);

    /**
     * update AdminSession by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminSession entity);

    /**
     * get AdminRule list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminRule list
     */
    Page<AdminSession> getPageList(Integer page, Integer size);
}

