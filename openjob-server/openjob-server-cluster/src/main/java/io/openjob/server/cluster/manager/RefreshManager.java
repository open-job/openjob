package io.openjob.server.cluster.manager;

import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.System;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Component
public class RefreshManager {

    private final SystemDAO systemDAO;
    private final ServerDAO serverDAO;

    private final JobSlotsDAO jobSlotsDAO;

    @Autowired
    public RefreshManager(SystemDAO systemDAO, ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO) {
        this.systemDAO = systemDAO;
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
    }

    public void refreshSystem(Boolean isUpdateClusterVersion) {
        // Update cluster version.
        if (isUpdateClusterVersion) {
            this.systemDAO.updateClusterVersion();
        }

        // Refresh system.
        System system = this.systemDAO.getOne();
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setMaxSlot(system.getMaxSlot());
        systemDTO.setClusterVersion(system.getClusterVersion());
        systemDTO.setClusterSupervisorSlot(system.getClusterSupervisorSlot());
        systemDTO.setWorkerSupervisorSlot(system.getWorkerSupervisorSlot());

        ClusterContext.refreshSystem(systemDTO);
        log.info(String.format("Refresh %s", system));
    }

    public void refreshClusterNodes() {
        List<Server> servers = serverDAO.listServers(ServerStatusEnum.OK.getStatus());
        ClusterUtil.refreshNodes(servers);
        log.info("Refresh nodes {}", servers);
    }

    public void refreshCurrentSlots(Long serverId) {
        List<JobSlots> jobSlots = jobSlotsDAO.listJobSlotsByServerId(serverId);
        Set<Long> currentSlots = jobSlots.stream().map(JobSlots::getId).collect(Collectors.toSet());
        ClusterContext.refreshCurrentSlots(currentSlots);

        log.info(String.format("Refresh slots %s", currentSlots));
    }
}
