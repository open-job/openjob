package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminConfig;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
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

    /**
     * get AdminConfig list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminConfig list
     */
    Page<AdminConfig> getPageList(Integer page, Integer size);
}

