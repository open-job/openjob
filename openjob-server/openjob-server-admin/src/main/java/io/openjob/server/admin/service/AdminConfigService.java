package io.openjob.server.admin.service;

import io.openjob.server.admin.request.AdminConfigAddRequest;
import io.openjob.server.admin.vo.AdminConfigAddVO;
import io.openjob.server.admin.request.AdminConfigQueryRequest;
import io.openjob.server.admin.vo.AdminConfigQueryVO;
import io.openjob.server.admin.request.AdminConfigUpdateRequest;
import io.openjob.server.admin.vo.AdminConfigUpdateVO;
import io.openjob.server.admin.request.AdminConfigDeleteRequest;
import io.openjob.server.admin.vo.AdminConfigDeleteVO;
import io.openjob.server.admin.request.AdminConfigListRequest;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @date 2022-11-15 14:15:29
 * @since 1.0.0
 */
public interface AdminConfigService  {

    /**
     * Add AdminConfig
     *
     * @param reqDTO reqDTO
     * @return AdminConfigAddVO
     */
    AdminConfigAddVO add(AdminConfigAddRequest reqDTO);

    /**
     * Get one AdminConfig
     *
     * @param reqDTO reqDTO
     * @return AdminConfigQueryVO
     */
    AdminConfigQueryVO query(AdminConfigQueryRequest reqDTO);

    /**
     * Update one AdminConfig
     *
     * @param reqDTO reqDTO
     * @return AdminConfigUpdateVO
     */
    AdminConfigUpdateVO update(AdminConfigUpdateRequest reqDTO);

    /**
     * Delete one AdminConfig
     *
     * @param reqDTO reqDTO
     * @return AdminConfigUpdateVO
     */
    AdminConfigUpdateVO delete(AdminConfigDeleteRequest reqDTO);

    /**
     * Get page list AdminConfig
     *
     * @param reqDTO reqDTO
     * @return AdminConfigListVO
     */
    PageDTO<AdminConfigQueryVO> getPageList(AdminConfigListRequest reqDTO);
}

