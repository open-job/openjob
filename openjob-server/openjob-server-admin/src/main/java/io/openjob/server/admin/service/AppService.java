package io.openjob.server.admin.service;

import io.openjob.server.admin.request.AddAppRequest;
import io.openjob.server.admin.request.ListAppRequest;
import io.openjob.server.admin.request.UpdateAppRequest;
import io.openjob.server.admin.vo.AddAppVO;
import io.openjob.server.admin.vo.ListAppVO;
import io.openjob.server.admin.vo.UpdateAppVO;

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
     * Add namespace.
     *
     * @param listRequest ListNamespaceRequest
     * @return ListNamespaceResponse
     */
    ListAppVO list(ListAppRequest listRequest);
}
