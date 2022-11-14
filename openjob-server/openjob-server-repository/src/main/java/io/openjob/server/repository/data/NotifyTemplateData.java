package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.NotifyTemplateDTO;
import io.openjob.server.repository.entity.NotifyTemplate;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-14 20:39:11
 * @since 1.0.0
 */
public interface NotifyTemplateData {

    /**
     * add NotifyTemplate
     *
     * @param dto dto
     * @return id
     */
    Long add(NotifyTemplateDTO dto);

    /**
     * batch add NotifyTemplate
     *
     * @param dtoList dto list
     */
    void batchAdd(List<NotifyTemplateDTO> dtoList);

    /**
     * get NotifyTemplate by ID
     *
     * @param id id
     * @return NotifyTemplate
     */
    NotifyTemplateDTO getById(Long id);

    /**
     * update NotifyTemplate by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(NotifyTemplateDTO dto);

    /**
     * get NotifyTemplate list by params
     *
     * @param id id
     * @return NotifyTemplate list
     */
    List<NotifyTemplateDTO> getPageList(Long id);

}

