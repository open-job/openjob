package io.openjob.server.admin.service;

import io.openjob.server.admin.request.AddNamespaceRequest;
import io.openjob.server.admin.request.ListNamespaceRequest;
import io.openjob.server.admin.request.UpdateNamespaceRequest;
import io.openjob.server.admin.request.UpdateStatusNamespaceRequest;
import io.openjob.server.admin.vo.AddNamespaceVO;
import io.openjob.server.admin.vo.ListNamespaceVO;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.admin.vo.UpdateNamespaceStatusVO;
import io.openjob.server.admin.vo.UpdateNamespaceVO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface NamespaceService {

    /**
     * Add namespace.
     *
     * @param addRequest AddNamespaceRequest
     * @return AddNamespaceResponse
     */
    AddNamespaceVO add(AddNamespaceRequest addRequest);

    /**
     * Add namespace.
     *
     * @param updateRequest UpdateNamespaceRequest
     * @return UpdateNamespaceResponse
     */
    UpdateNamespaceVO update(UpdateNamespaceRequest updateRequest);

    /**
     * Update namespace status
     *
     * @param updateStatusRequest UpdateNamespaceStatusRequest
     * @return UpdateStatusResult
     */
    UpdateNamespaceStatusVO updateStatus(UpdateStatusNamespaceRequest updateStatusRequest);

    /**
     * Add namespace.
     *
     * @param listRequest ListNamespaceRequest
     * @return ListNamespaceResponse
     */
    PageVO<ListNamespaceVO> list(ListNamespaceRequest listRequest);
}
