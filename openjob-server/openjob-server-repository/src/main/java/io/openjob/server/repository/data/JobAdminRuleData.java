package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.JobAdminRuleDTO;
import io.openjob.server.repository.entity.JobAdminRule;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:43:07
 * @since 1.0.0
 */
public interface JobAdminRuleData {

    /**
     * add JobAdminRule
     *
     * @param dto dto
     * @return id
     */
    Long add(JobAdminRuleDTO dto);

    /**
     * batch add JobAdminRule
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<JobAdminRuleDTO> dtoList);

    /**
     * get JobAdminRule by ID
     *
     * @param id id
     * @return JobAdminRule
     */
    JobAdminRuleDTO getById(Long id);

    /**
     * get JobAdminRule by ID, will try get from cache.
     *
     * @param id id
     * @return JobAdminRule
     */
    JobAdminRuleDTO getByIdFromCache(Long id);

    /**
     * update JobAdminRule by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(JobAdminRuleDTO dto);

}

