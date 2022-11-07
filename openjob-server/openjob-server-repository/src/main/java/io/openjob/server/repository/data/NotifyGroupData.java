package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.NotifyGroupDTO;
import io.openjob.server.repository.entity.NotifyGroup;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:21:32
 * @since 1.0.0
 */
public interface NotifyGroupData {

    /**
     * add NotifyGroup
     *
     * @param dto dto
     * @return id
     */
    Long add(NotifyGroupDTO dto);

    /**
     * batch add NotifyGroup
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<NotifyGroupDTO> dtoList);

    /**
     * get NotifyGroup by ID
     *
     * @param id id
     * @return NotifyGroup
     */
    NotifyGroupDTO getById(Long id);

    /**
     * get NotifyGroup by ID, will try get from cache.
     *
     * @param id id
     * @return NotifyGroup
     */
    NotifyGroupDTO getByIdFromCache(Long id);

    /**
     * update NotifyGroup by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(NotifyGroupDTO dto);

}

