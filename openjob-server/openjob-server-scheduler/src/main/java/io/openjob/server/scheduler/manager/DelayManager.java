package io.openjob.server.scheduler.manager;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.System;
import io.openjob.server.scheduler.data.DelayData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayManager {
    private final SystemDAO systemDAO;
    private final DelayData delayData;

    @Autowired
    public DelayManager(SystemDAO systemDAO, DelayData delayData) {
        this.systemDAO = systemDAO;
        this.delayData = delayData;
    }

    /**
     * Refresh delay version.
     *
     * @param topic       topic
     * @param id          id
     * @param failDelayId failDelayId
     */
    public void refreshDelayVersion(String topic, Long id, Long failDelayId) {
        System currentSystem = this.systemDAO.getOne();
        Long clusterVersion = currentSystem.getClusterVersion();
        Long delayVersion = currentSystem.getClusterDelayVersion();
        int clusterRows = this.systemDAO.updateClusterVersion(clusterVersion);
        int delayRows = this.systemDAO.updateClusterDelayVersion(currentSystem.getClusterDelayVersion());

        if (clusterRows <= 0) {
            throw new RuntimeException("Cluster version update failed!");
        }

        // Update success
        if (delayRows <= 0) {
            throw new RuntimeException("Delay version update failed!");
        }

        // Refresh system.
        clusterVersion++;
        delayVersion++;
        SystemDTO systemDTO = BeanMapperUtil.map(currentSystem, SystemDTO.class);
        systemDTO.setClusterVersion(clusterVersion);
        systemDTO.setClusterDelayVersion(delayVersion);

        // Delete cache
        this.delayData.deleteByTopicOrId(topic, id, failDelayId);

        ClusterContext.refreshSystem(systemDTO);
        log.info("Refresh delay version(cluster={} delay={}) {} ", clusterVersion, delayVersion, systemDTO);
    }
}
