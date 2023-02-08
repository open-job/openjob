package io.openjob.server.admin.service;

import io.openjob.server.admin.request.perm.AdminPermAddRequest;
import io.openjob.server.admin.request.perm.AdminPermDeleteRequest;
import io.openjob.server.admin.request.perm.AdminPermListRequest;
import io.openjob.server.admin.request.perm.AdminPermQueryRequest;
import io.openjob.server.admin.request.perm.AdminPermUpdateRequest;
import io.openjob.server.admin.vo.perm.AdminPermAddVO;
import io.openjob.server.admin.vo.perm.AdminPermQueryVO;
import io.openjob.server.admin.vo.perm.AdminPermUpdateVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminPermService {

    /**
     * Add AdminPerm
     *
     * @param reqDTO reqDTO
     * @return AdminPermAddVO
     */
    AdminPermAddVO add(AdminPermAddRequest reqDTO);

    /**
     * Get one AdminPerm
     *
     * @param reqDTO reqDTO
     * @return AdminPermQueryVO
     */
    AdminPermQueryVO query(AdminPermQueryRequest reqDTO);

    /**
     * Update one AdminPerm
     *
     * @param reqDTO reqDTO
     * @return AdminPermUpdateVO
     */
    AdminPermUpdateVO update(AdminPermUpdateRequest reqDTO);

    /**
     * Delete one AdminPerm
     *
     * @param reqDTO reqDTO
     * @return AdminPermUpdateVO
     */
    AdminPermUpdateVO delete(AdminPermDeleteRequest reqDTO);

    /**
     * Get page list AdminPerm
     *
     * @param reqDTO reqDTO
     * @return AdminPermListVO
     */
    PageDTO<AdminPermQueryVO> getPageList(AdminPermListRequest reqDTO);
}

