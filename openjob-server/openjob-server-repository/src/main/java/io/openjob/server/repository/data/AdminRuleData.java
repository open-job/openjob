package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminRuleDTO;
import io.openjob.server.repository.entity.AdminRule;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:35:13
 * @since 1.0.0
 */
public interface AdminRuleData {

    /**
     * add AdminRule
     *
     * @param dto dto
     * @return id
     */
    Long add(AdminRuleDTO dto);

    /**
     * batch add AdminRule
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<AdminRuleDTO> dtoList);

    /**
     * get AdminRule by ID
     *
     * @param id id
     * @return AdminRule
     */
    AdminRuleDTO getById(Long id);

    /**
     * get AdminRule by ID, will try get from cache.
     *
     * @param id id
     * @return AdminRule
     */
    AdminRuleDTO getByIdFromCache(Long id);

    /**
     * update AdminRule by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminRuleDTO dto);

}

