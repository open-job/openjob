package io.openjob.server.admin.service;

import io.openjob.server.admin.request.app.AddAppRequest;
import io.openjob.server.admin.request.app.ListAppRequest;
import io.openjob.server.admin.request.app.UpdateAppRequest;
import io.openjob.server.admin.request.app.UpdateAppStatusRequest;
import io.openjob.server.admin.vo.app.AddAppVO;
import io.openjob.server.admin.vo.app.ListAppVO;
import io.openjob.server.admin.vo.app.UpdateAppStatusVO;
import io.openjob.server.admin.vo.app.UpdateAppVO;
import io.openjob.server.common.vo.PageVO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface AppService {

    /**
     * Add namespace.
     *
     * @param addRequest AddNamespaceRequest
     * @return AddNamespaceResponse
     */
    AddAppVO add(AddAppRequest addRequest);

    /**
     * Add namespace.
     *
     * @param updateRequest UpdateNamespaceRequest
     * @return UpdateNamespaceResponse
     */
    UpdateAppVO update(UpdateAppRequest updateRequest);

    /**
     * Update
     *
     * @param updateRequest updateRequest
     * @return UpdateAppStatusVO
     */
    UpdateAppStatusVO updateStatus(UpdateAppStatusRequest updateRequest);

    /**
     * Add namespace.
     *
     * @param listRequest ListNamespaceRequest
     * @return PageVO
     */
    PageVO<ListAppVO> list(ListAppRequest listRequest);
}
