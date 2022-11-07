package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminConfigDTO;
import io.openjob.server.repository.entity.AdminConfig;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:35:56
 * @since 1.0.0
 */
public interface AdminConfigData {

    /**
     * add AdminConfig
     *
     * @param dto dto
     * @return id
     */
    Long add(AdminConfigDTO dto);

    /**
     * batch add AdminConfig
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<AdminConfigDTO> dtoList);

    /**
     * get AdminConfig by ID
     *
     * @param id id
     * @return AdminConfig
     */
    AdminConfigDTO getById(Long id);

    /**
     * get AdminConfig by ID, will try get from cache.
     *
     * @param id id
     * @return AdminConfig
     */
    AdminConfigDTO getByIdFromCache(Long id);

    /**
     * update AdminConfig by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminConfigDTO dto);

}

