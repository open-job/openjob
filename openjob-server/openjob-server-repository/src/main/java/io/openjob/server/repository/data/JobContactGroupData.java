package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.ContactGroupDTO;
import io.openjob.server.repository.entity.ContactGroup;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:45:22
 * @since 1.0.0
 */
public interface JobContactGroupData {

    /**
     * add JobContactGroup
     *
     * @param dto dto
     * @return id
     */
    Long add(ContactGroupDTO dto);

    /**
     * batch add JobContactGroup
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<ContactGroupDTO> dtoList);

    /**
     * get JobContactGroup by ID
     *
     * @param id id
     * @return JobContactGroup
     */
    ContactGroupDTO getById(Long id);

    /**
     * get JobContactGroup by ID, will try get from cache.
     *
     * @param id id
     * @return JobContactGroup
     */
    ContactGroupDTO getByIdFromCache(Long id);

    /**
     * update JobContactGroup by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(ContactGroupDTO dto);

}

