package io.openjob.server.scheduler.scheduler;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.System;
import io.openjob.server.scheduler.mapper.SchedulerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Component
public class DelayScheduler {
    private final SystemDAO systemDAO;

    @Autowired
    public DelayScheduler(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }

    /**
     * Refresh delay version.
     */
    public void refreshDelayVersion() {
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
        SystemDTO systemDTO = SchedulerMapper.INSTANCE.toSystemDTO(currentSystem);
        systemDTO.setClusterVersion(clusterVersion);
        systemDTO.setClusterDelayVersion(delayVersion);

        ClusterContext.refreshSystem(systemDTO);
        log.info("Refresh delay version(cluster={} delay={}) {} ", clusterVersion, delayVersion, systemDTO);
    }
}
