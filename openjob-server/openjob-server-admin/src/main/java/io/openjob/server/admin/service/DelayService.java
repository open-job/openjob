package io.openjob.server.admin.service;

import io.openjob.server.admin.request.delay.AddDelayRequest;
import io.openjob.server.admin.request.delay.DeleteDelayRequest;
import io.openjob.server.admin.request.delay.ListDelayRequest;
import io.openjob.server.admin.request.delay.UpdateDelayRequest;
import io.openjob.server.admin.request.delay.UpdateStatusDelayRequest;
import io.openjob.server.admin.vo.delay.AddDelayVO;
import io.openjob.server.admin.vo.delay.DeleteDelayVO;
import io.openjob.server.admin.vo.delay.ListDelayVO;
import io.openjob.server.admin.vo.delay.UpdateDelayVO;
import io.openjob.server.admin.vo.delay.UpdateStatusDelayVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin <swoftqq.com>
 * since 1.0.0
 */
public interface DelayService {

    /**
     * Add delay
     *
     * @param addRequest addRequest
     * @return AddDelayVO
     */
    AddDelayVO add(AddDelayRequest addRequest);

    /**
     * List delay
     *
     * @param listDelayRequest listDelayRequest
     * @return PageVO
     */
    PageVO<ListDelayVO> list(ListDelayRequest listDelayRequest);

    /**
     * Delete delay
     *
     * @param deleteDelayRequest deleteDelayRequest
     * @return DeleteDelayVO
     */
    DeleteDelayVO delete(DeleteDelayRequest deleteDelayRequest);


    /**
     * Update delay
     *
     * @param updateDelayRequest updateDelayRequest
     * @return UpdateDelayVO
     */
    UpdateDelayVO update(UpdateDelayRequest updateDelayRequest);

    /**
     * Update delay status
     *
     * @param updateRequest updateRequest
     * @return UpdateStatusDelayVO
     */
    UpdateStatusDelayVO updateStatus(UpdateStatusDelayRequest updateRequest);
}
