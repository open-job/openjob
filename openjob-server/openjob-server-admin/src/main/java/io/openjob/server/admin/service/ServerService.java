package io.openjob.server.admin.service;

import io.openjob.server.admin.request.server.ServerListRequest;
import io.openjob.server.admin.vo.server.ServerListVO;
import io.openjob.server.common.dto.PageDTO;

/**
 * @author riki
 * @date 2022-12-26
 */
public interface ServerService {

    /**
     * List Server by page
     * @param request list parmas
     * @return result
     */
    PageDTO<ServerListVO> getPageList(ServerListRequest request);
}
