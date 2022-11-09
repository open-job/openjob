package io.openjob.server.admin.service;

import io.openjob.server.admin.entity.JobAdminMenu;
import io.openjob.server.admin.request.JobAdminMenuAddRequest;
import io.openjob.server.admin.vo.JobAdminMenuAddVO;
import io.openjob.server.admin.request.JobAdminMenuDetailRequest;
import io.openjob.server.admin.vo.JobAdminMenuDetailVO;
import io.openjob.server.admin.request.JobAdminMenuUpdateRequest;
import io.openjob.server.admin.vo.JobAdminMenuUpdateVO;
import io.openjob.server.admin.request.JobAdminMenuDeleteRequest;
import io.openjob.server.admin.vo.JobAdminMenuDeleteVO;
import io.openjob.server.admin.request.JobAdminMenuListRequest;
import io.openjob.server.admin.vo.JobAdminMenuListVO;

/**
 * @author inhere
 * @date 2022-11-07 20:21:42
 * @since 1.0.0
 */
public interface AdminMenuService  {

    /**
     * Add JobAdminMenu
     *
     * @param reqDTO reqDTO
     * @return JobAdminMenuAddVO
     */
    AdminMenuAddVO add(AdminMenuAddRequest reqDTO);

    /**
     * Get one AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuDetailDTO
     */
    AdminMenuDetailDTO getById(AdminMenuQueryRequest reqDTO);

    /**
     * Update one AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuUpdateVO
     */
    AdminMenuUpdateVO update(AdminMenuUpdateRequest reqDTO);

    /**
     * @param reqDTO reqDTO
     * @return AdminMenuUpdateVO
     */
    AdminMenuUpdateVO deleteById(AdminMenuDeleteRequest reqDTO);

    /**
     * Get page list AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuListVO
     */
    PageDTO<AdminMenuListVO> getPageList(AdminMenuListRequest reqDTO);
}

