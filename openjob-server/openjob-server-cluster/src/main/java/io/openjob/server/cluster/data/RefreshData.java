package io.openjob.server.cluster.data;

import io.openjob.server.alarm.context.AlarmContext;
import io.openjob.common.context.Node;
import io.openjob.server.cluster.exception.ClusterNodeOperatingException;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.common.util.BeanMapperUtil;
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
public class RefreshData {

    private final SystemDAO systemDAO;
    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;
    private final WorkerDAO workerDAO;

    @Autowired
    public RefreshData(SystemDAO systemDAO, ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO, WorkerDAO workerDAO) {
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

        // Refresh alarm context
        this.refreshAlarmContext();
    }

    /**
     * Only refresh cluster version
     */
    public void refreshSystemClusterVersion() {
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

        // Refresh alarm context
        this.refreshAlarmContext();
    }

    /**
     * Refresh delay version
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
        SystemDTO systemDTO = BeanMapperUtil.map(currentSystem, SystemDTO.class);
        systemDTO.setClusterVersion(clusterVersion);
        systemDTO.setClusterDelayVersion(delayVersion);

        ClusterContext.refreshSystem(systemDTO);
        log.info("Refresh delay version(cluster={} delay={}) {} ", clusterVersion, delayVersion, systemDTO);
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

    /**
     * Refresh cluster alarm context
     */
    public void refreshAlarmContext() {
        // Refresh rules
        AlarmContext.refreshAlarmRules();

        // Refresh job map
        AlarmContext.refreshJobMap();

        // Refresh delay map
        AlarmContext.refreshDelayMap();
    }
}
