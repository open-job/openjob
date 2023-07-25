package io.openjob.server.scheduler.manager;

import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.System;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Component
public class ClusterVersionManager {

    private final SystemDAO systemDAO;

    @Autowired
    public ClusterVersionManager(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }

    public void refresh() {
        System currentSystem = this.systemDAO.getOne();
        Long clusterVersion = currentSystem.getClusterVersion();
        int clusterRows = this.systemDAO.updateClusterVersion(clusterVersion);

        if (clusterRows <= 0) {
            throw new RuntimeException("Cluster version update failed!");
        }

        // Refresh system.
        clusterVersion++;
        SystemDTO systemDTO = BeanMapperUtil.map(currentSystem, SystemDTO.class);
        systemDTO.setClusterVersion(clusterVersion);

        ClusterContext.refreshSystem(systemDTO);
        log.info("Refresh cluster version(cluster={} {} ", clusterVersion, systemDTO);
    }
}
