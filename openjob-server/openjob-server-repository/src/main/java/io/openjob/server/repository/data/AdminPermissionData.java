package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminPermissionDTO;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminPermissionData {

    /**
     * add AdminPermission
     *
     * @param dto dto
     * @return id
     */
    Long add(AdminPermissionDTO dto);

    /**
     * batch add AdminPermission
     *
     * @param dtoList dto list
     */
    void batchAdd(List<AdminPermissionDTO> dtoList);

    /**
     * get AdminPermission by ID
     *
     * @param id id
     * @return AdminPermission
     */
    AdminPermissionDTO getById(Long id);

    /**
     * update AdminPermission by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminPermissionDTO dto);

    /**
     * get AdminPermission list by params
     *
     * @param id id
     * @return AdminPermission list
     */
    List<AdminPermissionDTO> getPageList(Long id);



}

