package io.openjob.server.cluster.manager;

import io.openjob.common.context.Node;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
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

import java.util.List;
import java.util.Objects;
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
    private final WorkerDAO workerDAO;

    @Autowired
    public RefreshManager(SystemDAO systemDAO, ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO, WorkerDAO workerDAO) {
        this.systemDAO = systemDAO;
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
        this.workerDAO = workerDAO;
    }

    /**
     * Whether to refresh server.
     *
     * @return Boolean
     */
    public Boolean isRefreshServer() {
        Server server = this.serverDAO.getById(ClusterContext.getCurrentNode().getServerId());
        return Objects.nonNull(server) && ServerStatusEnum.OK.getStatus().equals(server.getStatus());
    }

    /**
     * Refresh system.
     *
     * @param isUpdateClusterVersion whether to update cluster version.
     */
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
        systemDTO.setDelayZsetMaxSlot(2);

        ClusterContext.refreshSystem(systemDTO);
        log.info(String.format("Refresh %s", system));
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
    }

    /**
     * Init current slots.
     */
    public void initCurrentSlots() {
        Node currentNode = ClusterContext.getCurrentNode();
        List<JobSlots> jobSlots = jobSlotsDAO.listJobSlotsByServerId(currentNode.getServerId());
        Set<Long> newCurrentSlots = jobSlots.stream().map(JobSlots::getId).collect(Collectors.toSet());
        ClusterContext.refreshCurrentSlots(newCurrentSlots);

        log.info(String.format("Refresh slots %s", newCurrentSlots));
    }

    /**
     * Refresh current slots.
     *
     * @return remove slots.
     */
    public Set<Long> refreshCurrentSlots() {
        Node currentNode = ClusterContext.getCurrentNode();
        Set<Long> currentSlots = ClusterContext.getCurrentSlots();
        List<JobSlots> jobSlots = jobSlotsDAO.listJobSlotsByServerId(currentNode.getServerId());
        Set<Long> newCurrentSlots = jobSlots.stream().map(JobSlots::getId).collect(Collectors.toSet());
        ClusterContext.refreshCurrentSlots(newCurrentSlots);

        log.info(String.format("Refresh slots %s", newCurrentSlots));

        // Node remove slots.
        currentSlots.removeAll(newCurrentSlots);
        return currentSlots;
    }
}
