package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.NotifyTemplateDTO;
import io.openjob.server.repository.entity.NotifyTemplate;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 13:44:57
 * @since 1.0.0
 */
public interface JobNotifyTemplateData {

    /**
     * add JobNotifyTemplate
     *
     * @param dto dto
     * @return id
     */
    Long add(NotifyTemplateDTO dto);

    /**
     * batch add JobNotifyTemplate
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<NotifyTemplateDTO> dtoList);

    /**
     * get JobNotifyTemplate by ID
     *
     * @param id id
     * @return JobNotifyTemplate
     */
    NotifyTemplateDTO getById(Long id);

    /**
     * get JobNotifyTemplate by ID, will try get from cache.
     *
     * @param id id
     * @return JobNotifyTemplate
     */
    NotifyTemplateDTO getByIdFromCache(Long id);

    /**
     * update JobNotifyTemplate by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(NotifyTemplateDTO dto);

}
