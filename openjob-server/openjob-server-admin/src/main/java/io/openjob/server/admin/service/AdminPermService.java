package io.openjob.server.admin.service;

import io.openjob.server.admin.request.perm.AdminPermissionMenusRequest;
import io.openjob.server.admin.vo.perm.AdminPermissionMenusVO;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminPermService {

    /**
     * Get permission menus.
     *
     * @param request request
     * @return AdminPermissionMenusVO
     */
    AdminPermissionMenusVO getMenus(AdminPermissionMenusRequest request);
}

