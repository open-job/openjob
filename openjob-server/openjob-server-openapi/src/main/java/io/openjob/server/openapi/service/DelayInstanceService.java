package io.openjob.server.openapi.service;

import io.openjob.server.openapi.request.DelayInstanceAddRequest;
import io.openjob.server.openapi.vo.DelayInstanceAddVO;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayInstanceService {
    DelayInstanceAddVO add(DelayInstanceAddRequest addRequest) ;
}
