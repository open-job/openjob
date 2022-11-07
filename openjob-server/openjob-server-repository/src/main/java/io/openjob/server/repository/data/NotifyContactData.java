package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.NotifyContactDTO;
import io.openjob.server.repository.entity.NotifyContact;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:42
 * @since 1.0.0
 */
public interface NotifyContactData {

    /**
     * add NotifyContact
     *
     * @param dto dto
     * @return id
     */
    Long add(NotifyContactDTO dto);

    /**
     * batch add NotifyContact
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<NotifyContactDTO> dtoList);

    /**
     * get NotifyContact by ID
     *
     * @param id id
     * @return NotifyContact
     */
    NotifyContactDTO getById(Long id);

    /**
     * get NotifyContact by ID, will try get from cache.
     *
     * @param id id
     * @return NotifyContact
     */
    NotifyContactDTO getByIdFromCache(Long id);

    /**
     * update NotifyContact by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(NotifyContactDTO dto);

}

