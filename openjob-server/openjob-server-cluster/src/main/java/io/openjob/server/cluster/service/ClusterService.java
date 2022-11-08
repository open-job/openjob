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
        Long clusterVersion = ClusterContext.getSystem().getClusterVersion();
        if (clusterVersion >= nodePingDTO.getClusterVersion()) {
            return;
        }

        if (this.refreshing.compareAndSet(false, true)) {
            return;
        }

        log.info("Begin to refreshing");
    }

    /**
     * Receive node join message.
     *
     * @param join join
     */
    public void receiveNodeJoin(NodeJoinDTO join) {
        if (this.refreshing.compareAndSet(false, true)) {
            return;
        }

        log.info("Join node starting {}({})", join.getAkkaAddress(), join.getServerId());

        // Refresh nodes.
        this.refreshManager.refreshClusterNodes();

        // Refresh slots.
        Set<Long> removeSlots = this.refreshManager.refreshCurrentSlots(true, true);

        // Remove job instance from timing wheel.
        this.wheelManager.removeBySlotsId(removeSlots);

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
        if (this.refreshing.compareAndSet(false, true)) {
            return;
        }

        log.info("Fail node starting {}({})", fail.getAkkaAddress(), fail.getServerId());

        // Refresh nodes.
        this.refreshManager.refreshClusterNodes();

        // Refresh slots.
        Set<Long> addSlots = this.refreshManager.refreshCurrentSlots(true, false);

        // Refresh system.
        this.refreshManager.refreshSystem(false);
        this.refreshing.set(false);

        log.info("Add new slots{}", addSlots);
        // Add job instance to timing wheel.
        log.info("Fail node starting {}({})", fail.getAkkaAddress(), fail.getServerId());
    }
}
