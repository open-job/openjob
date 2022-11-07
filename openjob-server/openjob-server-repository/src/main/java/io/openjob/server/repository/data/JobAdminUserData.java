package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.JobAdminUserDTO;
import io.openjob.server.repository.entity.JobAdminUser;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:33:41
 * @since 1.0.0
 */
public interface JobAdminUserData {

    /**
     * add JobAdminUser
     *
     * @param dto dto
     * @return id
     */
    Long add(JobAdminUserDTO dto);

    /**
     * batch add JobAdminUser
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<JobAdminUserDTO> dtoList);

    /**
     * get JobAdminUser by ID
     *
     * @param id id
     * @return JobAdminUser
     */
    JobAdminUserDTO getById(Long id);

    /**
     * get JobAdminUser by ID, will try get from cache.
     *
     * @param id id
     * @return JobAdminUser
     */
    JobAdminUserDTO getByIdFromCache(Long id);

    /**
     * update JobAdminUser by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(JobAdminUserDTO dto);

}

