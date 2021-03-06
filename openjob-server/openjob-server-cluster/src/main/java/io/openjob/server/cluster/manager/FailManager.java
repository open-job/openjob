package io.openjob.server.cluster.manager;

import com.google.common.collect.Maps;
import io.openjob.common.context.Node;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.dao.JobSlotsDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
@Log4j2
public class FailManager {
    private final ServerDAO serverDAO;
    private final JobSlotsDAO jobSlotsDAO;

    @Autowired
    public FailManager(ServerDAO serverDAO, JobSlotsDAO jobSlotsDAO) {
        this.serverDAO = serverDAO;
        this.jobSlotsDAO = jobSlotsDAO;
    }

    /**
     * Node fail.
     *
     * @param stopNode stopNode
     */
    @Transactional(rollbackFor = Exception.class)
    public void fail(Node stopNode) {
        // Update server status.
        this.serverDAO.update(stopNode.getServerId(), ServerStatusEnum.FAIL.getStatus());
        log.info("Update server to fail status {}", stopNode.getServerId());

        // Migrate slots.
        List<Server> servers = this.migrateSlots(stopNode);

        // Akka message for stop.
        this.sendClusterStopMessage(stopNode, servers);
    }

    /**
     * Migrate slots.
     *
     * @param stopNode stopNode
     * @return List
     */
    private List<Server> migrateSlots(Node stopNode) {
        List<JobSlots> currentJobSlots = this.jobSlotsDAO.listJobSlotsByServerId(stopNode.getServerId());
        List<Server> servers = this.serverDAO.listServers(ServerStatusEnum.OK.getStatus());

        // Exclude current server.
        int serverCount = servers.size();

        // Only one server.
        if (serverCount == 0) {
            jobSlotsDAO.updateByServerId(0L);
            log.info("Migrate slots to 0");
            return servers;
        }

        int index = 0;
        int slotsSize = (int) Math.floor((double) currentJobSlots.size() / serverCount);

        Map<Long, List<Long>> migrationSlots = Maps.newHashMap();
        for (int i = 0; i < servers.size(); i++) {
            Server s = servers.get(i);

            // Ignore current server.
            if (s.getId().equals(stopNode.getServerId())) {
                break;
            }

            // Last server.
            if (i + 1 == servers.size()) {
                List<Long> lastSlotsId = currentJobSlots.subList(index, currentJobSlots.size() - index)
                        .stream()
                        .map(JobSlots::getId)
                        .collect(Collectors.toList());
                migrationSlots.put(s.getId(), lastSlotsId);
                break;
            }

            index += slotsSize;

            List<Long> slotIds = currentJobSlots.subList(index, slotsSize)
                    .stream()
                    .map(JobSlots::getId)
                    .collect(Collectors.toList());
            migrationSlots.put(s.getId(), slotIds);
        }

        migrationSlots.forEach(this.jobSlotsDAO::updateByServerId);
        log.info("Migration slots {}", migrationSlots);
        return servers;
    }

    /**
     * Send cluster stop message.
     *
     * @param stopNode stopNode
     * @param servers  servers
     */
    private void sendClusterStopMessage(Node stopNode, List<Server> servers) {
        NodeFailDTO failDTO = new NodeFailDTO();
        failDTO.setIp(stopNode.getIp());
        failDTO.setServerId(stopNode.getServerId());
        failDTO.setAkkaAddress(stopNode.getAkkaAddress());
        ClusterUtil.sendMessage(failDTO, servers);
    }
}
