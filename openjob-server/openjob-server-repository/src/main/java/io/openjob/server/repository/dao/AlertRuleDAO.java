package io.openjob.server.repository.dao;

import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.entity.AlertRule;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
public interface AlertRuleDAO {

    /**
     * Save rule
     *
     * @param alertRule alertRule
     * @return Long
     */
    Long save(AlertRule alertRule);

    /**
     * Delete by id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * Update status
     *
     * @param id     id
     * @param status id
     */
    void updateStatus(Long id, Integer status);

    /**
     * Update rule
     *
     * @param alertRule alertRule
     * @return Long
     */
    Long update(AlertRule alertRule);

    /**
     * Get enable list.
     *
     * @return list
     */
    List<AlertRule> getEnableList();

    /**
     * Page list
     *
     * @param name name
     * @param page page
     * @param size size
     * @return PageDTO
     */
    PageDTO<AlertRule> pageList(String name, Integer page, Integer size);
}
