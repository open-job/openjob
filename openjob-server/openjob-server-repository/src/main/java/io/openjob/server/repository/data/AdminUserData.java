package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminUserDTO;
import io.openjob.server.repository.entity.AdminUser;

import java.util.List;

/**
 * @author inhere
 * @date 2022-11-07 21:35:46
 * @since 1.0.0
 */
public interface AdminUserData {

    /**
     * add AdminUser
     *
     * @param dto dto
     * @return id
     */
    Long add(AdminUserDTO dto);

    /**
     * batch add AdminUser
     *
     * @param dtoList dto list
     * @return id
     */
    Integer batchAdd(List<AdminUserDTO> dtoList);

    /**
     * get AdminUser by ID
     *
     * @param id id
     * @return AdminUser
     */
    AdminUserDTO getById(Long id);

    /**
     * get AdminUser by ID, will try get from cache.
     *
     * @param id id
     * @return AdminUser
     */
    AdminUserDTO getByIdFromCache(Long id);

    /**
     * update AdminUser by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminUserDTO dto);

}

