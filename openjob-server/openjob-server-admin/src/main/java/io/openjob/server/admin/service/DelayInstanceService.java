package io.openjob.server.admin.service;

import io.openjob.server.admin.request.delay.DeleteDelayInstanceRequest;
import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayInstanceRequest;
import io.openjob.server.admin.request.delay.StopDelayInstanceRequest;
import io.openjob.server.admin.vo.delay.DeleteDelayInstanceVO;
import io.openjob.server.admin.vo.delay.ListDelayInstanceVO;
import io.openjob.server.admin.vo.delay.StopDelayInstanceVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayInstanceService {

    /**
     * Page list
     *
     * @param request request
     * @return PageVO
     */
    PageVO<ListDelayInstanceVO> pageList(ListDelayInstanceRequest request);

    /**
     * Delete
     *
     * @param request request
     * @return DeleteDelayVO
     */
    DeleteDelayInstanceVO delete(DeleteDelayInstanceRequest request);

    /**
     * Stop
     *
     * @param request request
     * @return StopDelayInstanceVO
     */
    StopDelayInstanceVO stop(StopDelayInstanceRequest request);
}
