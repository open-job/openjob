package io.openjob.server.admin.service;

import io.openjob.server.admin.request.server.JobSlotRequest;
import io.openjob.server.admin.request.server.ServerListRequest;
import io.openjob.server.admin.vo.server.JobSlotListVO;
import io.openjob.server.admin.vo.server.ServerListVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author riki
 * @since 1.0.0
 */
public interface ServerService {

    /**
     * List Server by page
     *
     * @param request list parmas
     * @return PageVO
     */
    PageVO<ServerListVO> getServerList(ServerListRequest request);

    /**
     * List job slot by page
     *
     * @param request request
     * @return PageVO
     */
    PageVO<JobSlotListVO> getJobSlotsList(JobSlotRequest request);
}
