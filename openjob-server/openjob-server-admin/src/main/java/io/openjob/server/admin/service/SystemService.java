package io.openjob.server.admin.service;


import io.openjob.server.admin.request.system.SystemUpdateRequest;
import io.openjob.server.admin.vo.system.SystemUpdateVO;
import io.openjob.server.admin.vo.system.SystemVO;

/**
 * @author riki
 * @since 1.0.0
 */
public interface SystemService {

    /**
     * Get the latest System information
     * @return the latest System information
     */
    SystemVO getLatest();

    /**
     * update the latest System information
     * @param request update params
     * @return result
     */
    SystemUpdateVO update(SystemUpdateRequest request);
}
