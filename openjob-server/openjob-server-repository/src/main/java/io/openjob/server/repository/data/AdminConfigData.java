package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminConfigDTO;
import io.openjob.server.repository.entity.AdminConfig;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-14 20:16:25
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
     */
    void batchAdd(List<AdminConfigDTO> dtoList);

    /**
     * get AdminConfig by ID
     *
     * @param id id
     * @return AdminConfig
     */
    AdminConfigDTO getById(Long id);

    /**
     * update AdminConfig by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminConfigDTO dto);

    /**
     * get AdminConfig list by params
     *
     * @param id id
     * @return AdminConfig list
     */
    List<AdminConfigDTO> getPageList(Long id);

}

