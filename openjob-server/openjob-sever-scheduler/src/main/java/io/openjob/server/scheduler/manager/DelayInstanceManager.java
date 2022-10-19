package io.openjob.server.scheduler.manager;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.common.util.TaskUtil;
import io.openjob.server.common.util.SlotsUtil;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.autoconfigure.SchedulerProperties;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.dto.DelayInstanceAddRequestDTO;
import io.openjob.server.scheduler.dto.DelayInstanceAddResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayInstanceManager {
    private final DelayData delayData;

    private final DelayInstanceDAO delayInstanceDAO;

    private final SchedulerProperties schedulerProperties;

    @Autowired
    public DelayInstanceManager(DelayData delayData, DelayInstanceDAO delayInstanceDAO, SchedulerProperties schedulerProperties) {
        this.delayData = delayData;
        this.delayInstanceDAO = delayInstanceDAO;
        this.schedulerProperties = schedulerProperties;
    }

    public DelayInstanceAddResponseDTO add(DelayInstanceAddRequestDTO addRequest) {
        if (!this.schedulerProperties.getDelay().getEnable()) {
            throw new RuntimeException("Delay task is disable!");
        }

        String taskId = addRequest.getTaskId();
        if (Objects.isNull(taskId)) {
            taskId = TaskUtil.getRandomUniqueId();
        }

        Delay delay = this.delayData.getDelay(addRequest.getTopic());
        if (Objects.isNull(delay.getId())) {
            log.warn("Topic({}) is not exist!", addRequest.getTopic());
            throw new RuntimeException("");
        }

        int now = DateUtil.now();
        DelayInstance delayInstance = new DelayInstance();
        delayInstance.setNamespaceId(delay.getNamespaceId());
        delayInstance.setAppId(delay.getAppId());
        delayInstance.setTaskId(taskId);
        delayInstance.setTopic(addRequest.getTopic());
        delayInstance.setDelayId(delay.getId());
        delayInstance.setDelayParams(addRequest.getParams());
        delayInstance.setDelayExtra(addRequest.getExtra());
        delayInstance.setExecuteTime(addRequest.getExecuteTime());
        delayInstance.setCreateTime(now);
        delayInstance.setUpdateTime(now);
        delayInstance.setStatus(InstanceStatusEnum.WAITING.getStatus());
        delayInstance.setSlotsId(SlotsUtil.getSlotsId(taskId));
        this.delayInstanceDAO.save(delayInstance);

        DelayInstanceAddResponseDTO responseDTO = new DelayInstanceAddResponseDTO();
        responseDTO.setTaskId(taskId);
        return responseDTO;
    }
}
