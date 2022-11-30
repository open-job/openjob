package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.NotifyGroupDTO;

import java.util.List;

/**
 * @author inhere
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
     */
    void batchAdd(List<NotifyGroupDTO> dtoList);

    /**
     * get NotifyGroup by ID
     *
     * @param id id
     * @return NotifyGroup
     */
    NotifyGroupDTO getById(Long id);

    /**
     * update NotifyGroup by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(NotifyGroupDTO dto);

    /**
     * get NotifyGroup list by params
     *
     * @param id id
     * @return NotifyGroup list
     */
    List<NotifyGroupDTO> getPageList(Long id);

}

