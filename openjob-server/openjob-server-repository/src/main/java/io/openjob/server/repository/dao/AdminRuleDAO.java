package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminRule;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:35:12
 * @since 1.0.0
 */
public interface AdminRuleDAO {

    /**
     * add AdminRule
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminRule entity);

    /**
     * batch add AdminRule
     *
     * @param entityList entity list
     * @return number
     */
    void batchAdd(List<AdminRule> entityList);

    /**
     * get AdminRule by Id
     *
     * @param id id
     * @return AdminRule
     */
    AdminRule getById(Long id);

    /**
     * update AdminRule by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminRule entity);

}

