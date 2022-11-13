package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminRule;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:43:05
 * @since 1.0.0
 */
public interface JobAdminRuleDAO {

    /**
     * add JobAdminRule
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminRule entity);

    /**
     * batch add JobAdminRule
     *
     * @param entityList entity list
     * @return number
     */
    void batchAdd(List<AdminRule> entityList);

    /**
     * get JobAdminRule by Id
     *
     * @param id id
     * @return JobAdminRule
     */
    AdminRule getById(Long id);

    /**
     * update JobAdminRule by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminRule entity);

}

