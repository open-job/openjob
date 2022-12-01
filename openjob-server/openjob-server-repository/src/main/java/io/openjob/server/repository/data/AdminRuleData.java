package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminRuleDTO;

import java.util.List;

/**
 * @author inhere
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
     */
    void batchAdd(List<AdminRuleDTO> dtoList);

    /**
     * get AdminRule by ID
     *
     * @param id id
     * @return AdminRule
     */
    AdminRuleDTO getById(Long id);

    /**
     * get AdminRule list by IDs
     *
     * @param ids ids
     * @return AdminRule list
     */
    List<AdminRuleDTO> getByIds(List<Long> ids);

    /**
     * update AdminRule by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminRuleDTO dto);

    /**
     * get AdminRule list by params
     *
     * @param id id
     * @return AdminRule list
     */
    List<AdminRuleDTO> getPageList(Long id);

}

