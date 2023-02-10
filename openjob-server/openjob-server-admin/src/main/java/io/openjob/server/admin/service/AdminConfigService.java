package io.openjob.server.admin.service;

import io.openjob.server.admin.request.admin.AdminConfigAddRequest;
import io.openjob.server.admin.request.admin.AdminConfigDeleteRequest;
import io.openjob.server.admin.request.admin.AdminConfigListRequest;
import io.openjob.server.admin.request.admin.AdminConfigQueryRequest;
import io.openjob.server.admin.request.admin.AdminConfigUpdateRequest;
import io.openjob.server.admin.vo.admin.AdminConfigAddVO;
import io.openjob.server.admin.vo.admin.AdminConfigQueryVO;
import io.openjob.server.admin.vo.admin.AdminConfigUpdateVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface AdminConfigService {

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

