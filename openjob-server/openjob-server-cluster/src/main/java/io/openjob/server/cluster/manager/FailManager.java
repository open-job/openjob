package io.openjob.server.cluster.manager;

import com.google.common.collect.Maps;
import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.context.Node;
import io.openjob.server.cluster.autoconfigure.ClusterProperties;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeShutdownDTO;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import io.openjob.server.scheduler.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class FailManager {
    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;
    private final ClusterProperties clusterProperties;
    private final RefreshManager refreshManager;
    private final Scheduler scheduler;

    @Autowired
    public FailManager(ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO, ClusterProperties clusterProperties, RefreshManager refreshManager, Scheduler scheduler) {
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
        this.clusterProperties = clusterProperties;
        this.refreshManager = refreshManager;
        this.scheduler = scheduler;
    }

    /**
     * Do fail.
     *
     * @param stopNode stopNode
     */
    public void fail(Node stopNode) {
        try {
            // Do node fail.
            boolean result = ClusterUtil.clusterNodeOperate(
                    this.clusterProperties.getSpreadRetryTimes(),
                    () -> OpenjobSpringContext.getBean(this.getClass()).doFail(stopNode));

            // Success to send cluster message.
            if (result) {
                // Akka message for stop.
                NodeFailDTO failDTO = new NodeFailDTO();
                failDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
                failDTO.setIp(stopNode.getIp());
                failDTO.setServerId(stopNode.getServerId());
                failDTO.setAkkaAddress(stopNode.getAkkaAddress());

                // Akka message for fail.
                this.sendClusterStopMessage(failDTO, stopNode);
            }
        } catch (InterruptedException interruptedException) {
            log.info("Node fail error!", interruptedException);
        }
    }

    /**
     * Do node fail.
     *
     * @param stopNode stopNode
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean doFail(Node stopNode) {
        // Refresh system.
        // Lock system cluster version.
        this.refreshManager.refreshSystem(true);

        // Update server status.
        Integer effectRows = this.serverDAO.update(stopNode.getServerId(), ServerStatusEnum.FAIL.getStatus(), ServerStatusEnum.OK.getStatus());
        if (effectRows <= 0) {
            log.info("Node has failed! {}", stopNode);
            return false;
        }

        log.info("Update server to fail status {}", stopNode.getServerId());

        // Migrate slots.
        this.migrateSlots(stopNode);

        // Not shutdown.
        // Refresh nodes.
        this.refreshManager.refreshClusterNodes();

        // Refresh slots.
        this.refreshManager.refreshCurrentSlots();

        // Refresh scheduler.
        this.scheduler.refresh(Collections.emptySet());
        return true;
    }

    /**
     * Shutdown node.
     *
     * @param stopNode stop node.
     */
    public void shutdown(Node stopNode) {
        NodeShutdownDTO shutdown = new NodeShutdownDTO();
        shutdown.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
        shutdown.setIp(stopNode.getIp());
        shutdown.setServerId(stopNode.getServerId());
        shutdown.setAkkaAddress(stopNode.getAkkaAddress());

        // Akka message for stop.
        this.sendClusterStopMessage(shutdown, stopNode);
    }

    /**
     * Migrate slots.
     *
     * @param stopNode stopNode
     */
    private void migrateSlots(Node stopNode) {
        List<JobSlots> currentJobSlots = this.jobSlotsDAO.listJobSlotsByServerId(stopNode.getServerId());
        List<Server> servers = this.serverDAO.listServers(ServerStatusEnum.OK.getStatus())
                .stream().filter(s -> !s.getId().equals(stopNode.getServerId()))
                .collect(Collectors.toList());

        // Exclude current server.
        int serverCount = servers.size();

        // Only one server.
        if (serverCount == 0) {
            jobSlotsDAO.updateByServerId(0L);
            log.info("Migrate slots to 0");
            return;
        }

        int index = 0;
        int slotsSize = (int) Math.floor((double) currentJobSlots.size() / serverCount);

        Map<Long, List<Long>> migrationSlots = Maps.newHashMap();
        for (int i = 0; i < servers.size(); i++) {
            Server s = servers.get(i);

            // Last server.
            if (i + 1 == servers.size()) {
                List<Long> lastSlotsId = currentJobSlots.subList(index, currentJobSlots.size())
                        .stream()
                        .map(JobSlots::getId)
                        .collect(Collectors.toList());
                migrationSlots.put(s.getId(), lastSlotsId);
                break;
            }

            int segmentSize = i * slotsSize;
            List<Long> slotIds = currentJobSlots.subList(index, segmentSize)
                    .stream()
                    .map(JobSlots::getId)
                    .collect(Collectors.toList());

            index = segmentSize;
            migrationSlots.put(s.getId(), slotIds);
        }

        migrationSlots.forEach(this.jobSlotsDAO::updateByServerId);
        log.info("Migration slots {}", migrationSlots);
    }

    /**
     * Send cluster stop message.
     *
     * @param message  message
     * @param stopNode stop node.
     */
    private void sendClusterStopMessage(Object message, Node stopNode) {
        // Exclude current node.
        Set<Long> excludeNodes = new HashSet<>();
        excludeNodes.add(stopNode.getServerId());

        Boolean result = ClusterUtil.sendMessage(message, ClusterContext.getCurrentNode(), this.clusterProperties.getSpreadSize(), excludeNodes);
        if (!result) {
            log.info("Send message result is success! {} {}", message, stopNode);
        }
    }
}
