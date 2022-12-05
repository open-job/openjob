package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminRoleDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminRoleData {

    /**
     * add AdminRole
     *
     * @param dto dto
     * @return id
     */
    Long add(AdminRoleDTO dto);

    /**
     * batch add AdminRole
     *
     * @param dtoList dto list
     */
    void batchAdd(List<AdminRoleDTO> dtoList);

    /**
     * get AdminRole by ID
     *
     * @param id id
     * @return AdminRole
     */
    AdminRoleDTO getById(Long id);

    /**
     * get AdminRole list by IDs
     *
     * @param ids ids
     * @return AdminRole list
     */
    List<AdminRoleDTO> getByIds(List<Long> ids);

    /**
     * update AdminRole by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminRoleDTO dto);

    /**
     * get AdminRole list by params
     *
     * @param page page
     * @param size size
     * @return AdminRole list
     */
    Page<AdminRoleDTO> getPageList(Integer page, Integer size);

}

