package io.openjob.server.admin.service;

import io.openjob.server.admin.request.namespace.AddNamespaceRequest;
import io.openjob.server.admin.request.namespace.ListNamespaceRequest;
import io.openjob.server.admin.request.namespace.UpdateNamespaceRequest;
import io.openjob.server.admin.request.namespace.UpdateStatusNamespaceRequest;
import io.openjob.server.admin.request.namespace.DeleteNamespaceRequest;
import io.openjob.server.admin.vo.namespace.DeleteNamespaceVO;
import io.openjob.server.admin.vo.namespace.AddNamespaceVO;
import io.openjob.server.admin.vo.namespace.ListNamespaceVO;
import io.openjob.server.common.vo.PageVO;
import io.openjob.server.admin.vo.namespace.UpdateNamespaceStatusVO;
import io.openjob.server.admin.vo.namespace.UpdateNamespaceVO;

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
     * Delete namespace
     *
     * @param deleteNamespaceRequest deleteNamespaceRequest
     * @return UpdateNamespaceVO
     */
    DeleteNamespaceVO delete(DeleteNamespaceRequest deleteNamespaceRequest);

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
