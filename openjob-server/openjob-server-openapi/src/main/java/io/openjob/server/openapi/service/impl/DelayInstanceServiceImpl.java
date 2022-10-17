package io.openjob.server.openapi.service.impl;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.server.common.util.SlotsUtil;
import io.openjob.server.openapi.request.DelayInstanceAddRequest;
import io.openjob.server.openapi.service.DelayInstanceService;
import io.openjob.server.openapi.vo.DelayInstanceAddVO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.data.DelayData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Service
public class DelayInstanceServiceImpl implements DelayInstanceService {
    private final DelayInstanceDAO delayInstanceDAO;
    private final DelayData delayData;

    @Autowired
    public DelayInstanceServiceImpl(DelayInstanceDAO delayInstanceDAO, DelayData delayData) {
        this.delayInstanceDAO = delayInstanceDAO;
        this.delayData = delayData;
    }

    @Override
    public DelayInstanceAddVO add(DelayInstanceAddRequest instanceAddRequest) {
        String taskId = instanceAddRequest.getTaskId();
        if (Objects.isNull(taskId)) {
            taskId = TaskUtil.getRandomUniqueId();
        }

        Delay delay = this.delayData.getDelay(instanceAddRequest.getNamespaceId(), instanceAddRequest.getTopic());
        if (Objects.isNull(delay.getId())) {
            throw new RuntimeException(String.format("Topic(%s) is not exist!", instanceAddRequest.getTopic()));
        }

        int now = DateUtil.now();
        DelayInstance delayInstance = new DelayInstance();
        delayInstance.setNamespaceId(instanceAddRequest.getNamespaceId());
        delayInstance.setAppId(delay.getAppId());
        delayInstance.setTaskId(taskId);
        delayInstance.setTopic(instanceAddRequest.getTopic());
        delayInstance.setDelayId(delay.getId());
        delayInstance.setDelayParams(instanceAddRequest.getParams());
        delayInstance.setDelayExtra(instanceAddRequest.getExtra());
        delayInstance.setExecuteTime(instanceAddRequest.getExecuteTime());
        delayInstance.setCreateTime(now);
        delayInstance.setUpdateTime(now);
        delayInstance.setStatus(InstanceStatusEnum.WAITING.getStatus());
        delayInstance.setSlotsId(SlotsUtil.getSlotsId(taskId));
        this.delayInstanceDAO.save(delayInstance);

        DelayInstanceAddVO delayInstanceAddVO = new DelayInstanceAddVO();
        delayInstanceAddVO.setTaskId(instanceAddRequest.getTaskId());
        return delayInstanceAddVO;
    }
}
