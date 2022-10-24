package io.openjob.server.openapi.service.impl;

import io.openjob.server.openapi.request.DelayInstanceAddRequest;
import io.openjob.server.openapi.service.DelayInstanceService;
import io.openjob.server.openapi.vo.DelayInstanceAddVO;
import io.openjob.server.repository.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.repository.dto.DelayInstanceAddResponseDTO;
import io.openjob.server.repository.manager.DelayInstanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayInstanceServiceImpl implements DelayInstanceService {
    private final DelayInstanceManager delayInstanceManager;

    @Autowired
    public DelayInstanceServiceImpl(DelayInstanceManager delayInstanceManager) {
        this.delayInstanceManager = delayInstanceManager;
    }

    @Override
    public DelayInstanceAddVO add(DelayInstanceAddRequest addRequest) {
        DelayInstanceAddRequestDTO addRequestDTO = new DelayInstanceAddRequestDTO();
        addRequestDTO.setTaskId(addRequest.getTaskId());
        addRequestDTO.setTopic(addRequest.getTopic());
        addRequestDTO.setParams(addRequest.getParams());
        addRequestDTO.setExtra(addRequest.getExtra());
        addRequestDTO.setExecuteTime(addRequest.getExecuteTime());

        DelayInstanceAddResponseDTO addResponseDTO = this.delayInstanceManager.add(addRequestDTO);
        DelayInstanceAddVO delayInstanceAddVO = new DelayInstanceAddVO();
        delayInstanceAddVO.setTaskId(addResponseDTO.getTaskId());
        return delayInstanceAddVO;
    }
}
