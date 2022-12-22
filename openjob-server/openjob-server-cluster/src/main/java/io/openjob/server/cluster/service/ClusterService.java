package io.openjob.server.cluster.service;

import io.openjob.common.context.Node;
import io.openjob.server.cluster.autoconfigure.ClusterProperties;
import io.openjob.server.cluster.dto.NodeFailDTO;
import io.openjob.server.cluster.dto.NodeJoinDTO;
import io.openjob.server.cluster.dto.NodePingDTO;
import io.openjob.server.cluster.dto.NodeShutdownDTO;
import io.openjob.server.cluster.dto.WorkerFailDTO;
import io.openjob.server.cluster.dto.WorkerJoinDTO;
import io.openjob.server.cluster.manager.FailManager;
import io.openjob.server.cluster.manager.RefreshManager;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.scheduler.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Service
public class ClusterService {

    private final RefreshManager refreshManager;
    private final Scheduler scheduler;
    private final ClusterProperties clusterProperties;
    private final FailManager failManager;

    /**
     * Refresh status.
     */
    private final AtomicBoolean refreshing = new AtomicBoolean(false);
    private final AtomicBoolean nodeRunning = new AtomicBoolean(false);

    @Autowired
    public ClusterService(RefreshManager refreshManager, Scheduler scheduler, ClusterProperties clusterProperties, FailManager failManager) {
        this.refreshManager = refreshManager;
        this.scheduler = scheduler;
        this.clusterProperties = clusterProperties;
        this.failManager = failManager;
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

        try {
            log.info("Node ping refresh beginning!");

            // Refresh nodes.
            this.refreshManager.refreshClusterNodes();

            // Refresh current slots.
            Set<Long> removeSlots = this.refreshManager.refreshCurrentSlots();

            // Refresh scheduler.
            this.scheduler.refresh(removeSlots);

            // Refresh app workers.
            this.refreshManager.refreshAppWorkers();

            // Refresh system.
            this.refreshManager.refreshSystem(false);
        } finally {
            log.info("Node ping refresh completed!");
            this.refreshing.set(false);
        }
    }

    /**
     * Receive node join message.
     *
     * @param join join
     */
    public void receiveNodeJoin(NodeJoinDTO join) {
        // Ignore
        if (this.isIgnore(join.getClusterVersion())) {
            log.info("Node join ignore! {}", join);
            return;
        }

        try {
            log.info("Node join! {}({})", join.getAkkaAddress(), join.getServerId());

            // Refresh nodes.
            this.refreshManager.refreshClusterNodes();

            // Refresh slots.
            Set<Long> removeSlots = this.refreshManager.refreshCurrentSlots();

            // Refresh scheduler.
            this.scheduler.refresh(removeSlots);

            // Refresh system.
            this.refreshManager.refreshSystem(false);

            // Forward message.
            this.forwardMessage(join);
        } finally {
            this.refreshing.set(false);
            log.info("Node join! {}({})", join.getAkkaAddress(), join.getServerId());
        }
    }

    /**
     * Receive node fail message.
     *
     * @param fail fail
     */
    public void receiveNodeFail(NodeFailDTO fail) {
        // Ignore
        if (this.isIgnore(fail.getClusterVersion())) {
            log.info("Node fail ignore! {}", fail);
            return;
        }

        try {
            log.info("Node fail {}({})", fail.getAkkaAddress(), fail.getServerId());

            // Refresh nodes.
            this.refreshManager.refreshClusterNodes();

            // Refresh slots.
            this.refreshManager.refreshCurrentSlots();

            // Refresh system.
            this.refreshManager.refreshSystem(false);

            // Forward message.
            this.forwardMessage(fail);
        } finally {
            this.refreshing.set(false);

            // Add job instance to timing wheel.
            log.info("Node fail {}({})", fail.getAkkaAddress(), fail.getServerId());
        }
    }

    /**
     * Receive node shutdown request.
     *
     * @param shutdown shutdown node.
     */
    public void receiveNodeShutdown(NodeShutdownDTO shutdown) {
        Node stopNode = new Node();
        stopNode.setAkkaAddress(shutdown.getAkkaAddress());
        stopNode.setIp(shutdown.getIp());
        stopNode.setServerId(shutdown.getServerId());
        this.failManager.fail(stopNode);
    }

    /**
     * Receive worker join.
     *
     * @param workerJoinDTO worker join.
     */
    public void receiveWorkerJoin(WorkerJoinDTO workerJoinDTO) {
        // Ignore
        if (this.isIgnore(workerJoinDTO.getClusterVersion())) {
            log.info("Worker join ignore! {}", workerJoinDTO);
            return;
        }

        try {
            // Refresh system.
            this.refreshManager.refreshSystem(false);

            // Refresh app workers.
            this.refreshManager.refreshAppWorkers();

            // Forward message.
            this.forwardMessage(workerJoinDTO);
        } finally {
            this.refreshing.set(false);
            log.info("Worker join! {}", workerJoinDTO);
        }
    }

    /**
     * Receive worker fail.
     *
     * @param workerFailDTO worker fail.
     */
    public void receiveWorkerFail(WorkerFailDTO workerFailDTO) {
        // Ignore
        if (this.isIgnore(workerFailDTO.getClusterVersion())) {
            log.info("Worker fail ignore! {}", workerFailDTO);
            return;
        }

        try {
            // Refresh system.
            this.refreshManager.refreshSystem(false);

            // Refresh app workers.
            this.refreshManager.refreshAppWorkers();

            // Forward message.
            this.forwardMessage(workerFailDTO);
        } finally {
            this.refreshing.set(false);
            log.info("Worker fail! {}", workerFailDTO);
        }
    }

    public void setRunning() {
        this.nodeRunning.set(true);
    }

    /**
     * Ignore message.
     *
     * @param clusterVersion receive cluster version.
     * @return Boolean
     */
    private Boolean isIgnore(Long clusterVersion) {
        if (!this.nodeRunning.get()) {
            log.info("Cluster node is not running! Ignore cluster message.");
            return true;
        }

        if (clusterVersion <= ClusterContext.getSystem().getClusterVersion()) {
            return true;
        }

        return !this.refreshing.compareAndSet(false, true);
    }


    /**
     * Forward message.
     *
     * @param message akka message.
     */
    private void forwardMessage(Object message) {
        ClusterUtil.sendMessage(message, ClusterContext.getCurrentNode(), this.clusterProperties.getSpreadSize(), new HashSet<>());
    }
}
