package io.openjob.server.admin.service;

import io.openjob.server.admin.request.menu.AdminMenuAddRequest;
import io.openjob.server.admin.vo.menu.AdminMenuAddVO;
import io.openjob.server.admin.request.menu.AdminMenuQueryRequest;
import io.openjob.server.admin.vo.menu.AdminMenuQueryVO;
import io.openjob.server.admin.request.menu.AdminMenuUpdateRequest;
import io.openjob.server.admin.vo.menu.AdminMenuUpdateVO;
import io.openjob.server.admin.request.menu.AdminMenuDeleteRequest;
import io.openjob.server.admin.request.menu.AdminMenuListRequest;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @date 2022-11-13 23:24:47
 * @since 1.0.0
 */
public interface AdminMenuService  {

    /**
     * Add AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuAddVO
     */
    AdminMenuAddVO add(AdminMenuAddRequest reqDTO);

    /**
     * Get one AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuQueryVO
     */
    AdminMenuQueryVO query(AdminMenuQueryRequest reqDTO);

    /**
     * Update one AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuUpdateVO
     */
    AdminMenuUpdateVO update(AdminMenuUpdateRequest reqDTO);

    /**
     * Delete one AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuUpdateVO
     */
    AdminMenuUpdateVO delete(AdminMenuDeleteRequest reqDTO);

    /**
     * Get page list AdminMenu
     *
     * @param reqDTO reqDTO
     * @return AdminMenuListVO
     */
    PageDTO<AdminMenuQueryVO> getPageList(AdminMenuListRequest reqDTO);
}

