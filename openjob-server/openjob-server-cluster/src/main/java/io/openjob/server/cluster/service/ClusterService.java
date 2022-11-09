package io.openjob.server.cluster.service;

import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.manager.RefreshManager;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.scheduler.wheel.WheelManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
@Service
public class ClusterService {

    private final WheelManager wheelManager;

    private final RefreshManager refreshManager;

    /**
     * Refresh status.
     */
    private final AtomicBoolean refreshing = new AtomicBoolean(false);

    @Autowired
    public ClusterService(WheelManager wheelManager, RefreshManager refreshManager) {
        this.wheelManager = wheelManager;
        this.refreshManager = refreshManager;
    }

    /**
     * Receive node ping.
     *
     * @param nodePingDTO nodePingDTO
     */
    public void receiveNodePing(NodePingDTO nodePingDTO) {
        // Ignore
        if (this.isIgnore(nodePingDTO.getClusterVersion())) {
            return;
        }

        // Not refresh current node.
        // Server status is offline.
        if (!this.refreshManager.isRefreshServer()) {
            return;
        }

        // Refresh nodes.
        this.refreshManager.refreshClusterNodes();

        // Refresh current slots.
        Set<Long> removeSlots = this.refreshManager.refreshCurrentSlots();
        if (!removeSlots.isEmpty()) {
            this.wheelManager.removeBySlotsId(removeSlots);
        }

        // Refresh app workers.
        this.refreshManager.refreshAppWorkers();

        // Refresh system.
        this.refreshManager.refreshSystem(false);

        log.info("Begin to refreshing");
        this.refreshing.set(false);
    }

    /**
     * Receive node join message.
     *
     * @param join join
     */
    public void receiveNodeJoin(NodeJoinDTO join) {
        // Ignore
        if (this.isIgnore(join.getClusterVersion())) {
            return;
        }

        log.info("Join node starting {}({})", join.getAkkaAddress(), join.getServerId());

        // Refresh nodes.
        this.refreshManager.refreshClusterNodes();

        // Refresh slots.
        Set<Long> removeSlots = this.refreshManager.refreshCurrentSlots();

        // Remove job instance from timing wheel.
        if (!removeSlots.isEmpty()) {
            this.wheelManager.removeBySlotsId(removeSlots);
        }

        // Refresh system.
        this.refreshManager.refreshSystem(false);

        this.refreshing.set(false);
        log.info("Join node success {}({})", join.getAkkaAddress(), join.getServerId());
    }

    /**
     * Receive node fail message.
     *
     * @param fail fail
     */
    public void receiveNodeFail(NodeFailDTO fail) {
        // Ignore
        if (this.isIgnore(fail.getClusterVersion())) {
            return;
        }

        log.info("Fail node starting {}({})", fail.getAkkaAddress(), fail.getServerId());

        // Refresh nodes.
        this.refreshManager.refreshClusterNodes();

        // Refresh slots.
        this.refreshManager.refreshCurrentSlots();

        // Refresh system.
        this.refreshManager.refreshSystem(false);
        this.refreshing.set(false);

        // Add job instance to timing wheel.
        log.info("Fail node starting {}({})", fail.getAkkaAddress(), fail.getServerId());
    }

    /**
     * Ignore message.
     *
     * @param clusterVersion receive cluster version.
     * @return Boolean
     */
    private Boolean isIgnore(Long clusterVersion) {
        if (clusterVersion >= ClusterContext.getSystem().getClusterVersion()) {
            return true;
        }

        return !this.refreshing.compareAndSet(false, true);
    }
}
