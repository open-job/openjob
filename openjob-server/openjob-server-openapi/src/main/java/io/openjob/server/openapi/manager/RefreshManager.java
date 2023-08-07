package io.openjob.server.openapi.manager;

import io.openjob.common.context.Node;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.openapi.exception.ClusterNodeOperatingException;
import io.openjob.server.openapi.util.ClusterUtil;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.SystemDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.repository.entity.System;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class RefreshManager {

    private final SystemDAO systemDAO;
    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;
    private final WorkerDAO workerDAO;

    @Autowired
    public RefreshManager(SystemDAO systemDAO, ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO, WorkerDAO workerDAO) {
        this.systemDAO = systemDAO;
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
        this.workerDAO = workerDAO;
    }

    /**
     * Refresh system.
     *
     * @param isUpdateClusterVersion whether to update cluster version.
     */
    public void refreshSystem(Boolean isUpdateClusterVersion) {
        System currentSystem = this.systemDAO.getOne();
        Long currentVersion = currentSystem.getClusterVersion();

        // Update cluster version.
        if (isUpdateClusterVersion) {
            Integer effectRows = this.systemDAO.updateClusterVersion(currentSystem.getClusterVersion());
            // Lock cluster version fail.
            if (effectRows <= 0) {
                throw new ClusterNodeOperatingException("Node join or fail is running!");
            }

            // Not query db to fix repeatable read.
            currentVersion++;
        }

        // Refresh system.
        SystemDTO systemDTO = new SystemDTO();
        systemDTO.setMaxSlot(currentSystem.getMaxSlot());
        systemDTO.setWorkerSupervisorSlot(currentSystem.getWorkerSupervisorSlot());
        systemDTO.setDelayZsetSlot(currentSystem.getDelayZsetSlot());
        systemDTO.setDelayFailZsetSlot(currentSystem.getDelayFailZsetSlot());
        systemDTO.setDelayAddListSlot(currentSystem.getDelayAddListSlot());
        systemDTO.setDelayStatusListSlot(currentSystem.getDelayStatusListSlot());
        systemDTO.setDelayDeleteListSlot(currentSystem.getDelayDeleteListSlot());
        systemDTO.setClusterDelayVersion(currentSystem.getClusterDelayVersion());
        systemDTO.setJobKeepDays(currentSystem.getJobKeepDays());
        systemDTO.setDelayKeepDays(currentSystem.getDelayKeepDays());
        systemDTO.setServerKeepDays(currentSystem.getServerKeepDays());
        systemDTO.setWorkerKeepDays(currentSystem.getWorkerKeepDays());
        systemDTO.setClusterVersion(currentVersion);

        ClusterContext.refreshSystem(systemDTO);
        log.info("Refresh cluster version({}) {} ", currentSystem.getClusterVersion(), systemDTO);
    }

    /**
     * Refresh cluster nodes.
     */
    public void refreshClusterNodes() {
        List<Server> servers = serverDAO.listServers(ServerStatusEnum.OK.getStatus());
        ClusterUtil.refreshNodes(servers);
        log.info("Refresh nodes {}", servers);
    }

    /**
     * Refresh app workers.
     */
    public void refreshAppWorkers() {
        List<Worker> workers = workerDAO.listOnlineWorkers();
        ClusterUtil.refreshAppWorkers(workers);
        log.info("Refresh workers {}", workers.stream().map(Worker::getAddress).collect(Collectors.toList()));
    }

    /**
     * Refresh current slots.
     *
     * @return remove slots.
     */
    public Set<Long> refreshCurrentSlots() {
        // Node remove slots.
        Set<Long> removeSlots = new HashSet<>(ClusterContext.getCurrentSlots());

        // New current slots.
        Node currentNode = ClusterContext.getCurrentNode();
        List<JobSlots> jobSlots = jobSlotsDAO.listJobSlotsByServerId(currentNode.getServerId());
        Set<Long> newCurrentSlots = jobSlots.stream().map(JobSlots::getId).collect(Collectors.toSet());
        ClusterContext.refreshCurrentSlots(newCurrentSlots);

        log.info(String.format("Refresh slots %s", newCurrentSlots));
        removeSlots.removeAll(newCurrentSlots);
        return removeSlots;
    }
}
