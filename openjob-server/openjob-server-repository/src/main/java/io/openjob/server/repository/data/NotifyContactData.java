package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.NotifyContactDTO;

import java.util.List;

/**
 * @author inhere
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
     */
    void batchAdd(List<NotifyContactDTO> dtoList);

    /**
     * get NotifyContact by ID
     *
     * @param id id
     * @return NotifyContact
     */
    NotifyContactDTO getById(Long id);

    /**
     * update NotifyContact by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(NotifyContactDTO dto);

    /**
     * get NotifyContact list by params
     *
     * @param id id
     * @return NotifyContact list
     */
    List<NotifyContactDTO> getPageList(Long id);

}

