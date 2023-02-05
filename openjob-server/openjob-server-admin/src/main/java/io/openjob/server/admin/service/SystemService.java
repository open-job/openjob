package io.openjob.server.admin.service;


import io.openjob.server.admin.request.system.AdminSystemUpdateRequest;
import io.openjob.server.admin.vo.system.AdminSystemUpdateVO;
import io.openjob.server.admin.vo.system.AdminSystemVO;

/**
 * @author riki
 * @since 1.0.0
 */
public interface SystemService {

    /**
     * Get the latest System information
     * @return the latest System information
     */
    AdminSystemVO getLatest();

    /**
     * update the latest System information
     * @param request update params
     * @return result
     */
    AdminSystemUpdateVO update(AdminSystemUpdateRequest request);
}
