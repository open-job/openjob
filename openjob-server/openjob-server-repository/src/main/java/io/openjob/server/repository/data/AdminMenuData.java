package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminMenuDTO;
import io.openjob.server.repository.entity.AdminMenu;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:34:58
 * @since 1.0.0
 */
public interface AdminMenuData {

    /**
     * add AdminMenu
     *
     * @param dto dto
     * @return id
     */
    Long add(AdminMenuDTO dto);

    /**
     * batch add AdminMenu
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<AdminMenuDTO> dtoList);

    /**
     * get AdminMenu by ID
     *
     * @param id id
     * @return AdminMenu
     */
    AdminMenuDTO getById(Long id);

    /**
     * get AdminMenu by ID, will try get from cache.
     *
     * @param id id
     * @return AdminMenu
     */
    AdminMenuDTO getByIdFromCache(Long id);

    /**
     * update AdminMenu by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminMenuDTO dto);

}

