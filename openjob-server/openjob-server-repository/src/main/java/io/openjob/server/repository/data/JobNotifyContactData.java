package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.NotifyContactDTO;
import io.openjob.server.repository.entity.NotifyContact;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:45:11
 * @since 1.0.0
 */
public interface JobNotifyContactData {

    /**
     * add JobNotifyContact
     *
     * @param dto dto
     * @return id
     */
    Long add(NotifyContactDTO dto);

    /**
     * batch add JobNotifyContact
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<NotifyContactDTO> dtoList);

    /**
     * get JobNotifyContact by ID
     *
     * @param id id
     * @return JobNotifyContact
     */
    NotifyContactDTO getById(Long id);

    /**
     * get JobNotifyContact by ID, will try get from cache.
     *
     * @param id id
     * @return JobNotifyContact
     */
    NotifyContactDTO getByIdFromCache(Long id);

    /**
     * update JobNotifyContact by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(NotifyContactDTO dto);

}
