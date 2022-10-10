package io.openjob.server.openapi.service.impl;

import io.openjob.common.util.TaskUtil;
import io.openjob.server.openapi.request.DelayInstanceAddRequest;
import io.openjob.server.openapi.service.DelayInstanceService;
import io.openjob.server.openapi.vo.DelayInstanceAddVO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayInstanceServiceImpl implements DelayInstanceService {
    private final DelayInstanceDAO delayInstanceDAO;

    @Autowired
    public DelayInstanceServiceImpl(DelayInstanceDAO delayInstanceDAO) {
        this.delayInstanceDAO = delayInstanceDAO;
    }

    @Override
    public DelayInstanceAddVO add(DelayInstanceAddRequest addRequest) {
        String taskId = addRequest.getTaskId();
        if (Objects.isNull(taskId)) {
            taskId = TaskUtil.getRandomUniqueId();
        }

        DelayInstanceAddVO delayInstanceAddVO = new DelayInstanceAddVO();
        delayInstanceAddVO.setTaskId(addRequest.getTaskId());
        return delayInstanceAddVO;
    }
}
