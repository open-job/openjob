package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminMenuDTO;

import java.util.List;

/**
 * @author inhere
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
     */
    void batchAdd(List<AdminMenuDTO> dtoList);

    /**
     * get AdminMenu by ID
     *
     * @param id id
     * @return AdminMenu
     */
    AdminMenuDTO getById(Long id);

    /**
     * update AdminMenu by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminMenuDTO dto);

    /**
     * get AdminMenu list by params
     *
     * @param id id
     * @return AdminMenu list
     */
    List<AdminMenuDTO> getPageList(Long id);

    /**
     * get AdminMenu list by IDs
     *
     * @param ids ids
     * @return AdminMenu list
     */
    List<AdminMenuDTO> getByIds(List<Long> ids);

    /**
     * get all valid AdminMenu list
     *
     * @return AdminMenu list
     */
    List<AdminMenuDTO> getAllMenus();
}

