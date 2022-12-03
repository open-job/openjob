package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.AdminRule;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
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

    /**
     * get AdminRule list by IDs
     *
     * @param ids ids
     * @return AdminRule list
     */
    List<AdminRule> getByIds(List<Long> ids);

    /**
     * get AdminRule list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminRule list
     */
    Page<AdminRule> getPageList(Integer page, Integer size);
}

