package io.openjob.server.repository.dao;

import io.openjob.server.repository.constant.PermissionTypeEnum;
import io.openjob.server.repository.entity.AdminPermission;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminPermissionDAO {

    /**
     * add AdminPermission
     *
     * @param entity entity
     * @return id
     */
    Long add(AdminPermission entity);

    /**
     * batch add AdminPermission
     *
     * @param entityList entity list
     */
    void batchAdd(List<AdminPermission> entityList);

    /**
     * get AdminPermission by Id
     *
     * @param id id
     * @return AdminPermission
     */
    AdminPermission getById(Long id);

    /**
     * update AdminPermission by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(AdminPermission entity);

    /**
     * get AdminPermission list by ID
     *
     * @param ids ids
     * @return AdminPermission list
     */
    List<AdminPermission> getByIds(List<Long> ids);

    /**
     * get AdminPermission list by page, size
     *
     * @param page page
     * @param size size
     * @return AdminPermission list
     */
    Page<AdminPermission> getPageList(Integer page, Integer size);

    /**
     * Get all AdminPermission list
     *
     * @param permissionType permissionType
     * @return list
     */
    List<AdminPermission> getPermissionList(PermissionTypeEnum permissionType);
}

