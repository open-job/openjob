package io.openjob.server.common;

import akka.actor.ActorRef;
import com.google.common.collect.Maps;
import io.openjob.common.context.Node;
import io.openjob.server.common.dto.WorkerDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ClusterContext {

    /**
     * Cluster actor reference.
     */
    private static ActorRef clusterActorRef;

    /**
     * Current node.
     */
    private static Node currentNode;

    /**
     * Current slots
     */
    private static final Set<Long> CURRENT_SLOTS = new HashSet<>();

    /**
     * Slots list map.
     * Key is slots id, Value is server id.
     */
    private static final Map<Long, Long> SLOTS_LIST_MAP = Maps.newHashMap();

    /**
     * Cluster nodes.
     * Key is server id, Value is server node.
     */
    private static final Map<Long, Node> NODES_LIST = Maps.newConcurrentMap();

    /**
     * App worker list.
     */
    private static final Map<Long, List<WorkerDTO>> APP_WORKERS = Maps.newConcurrentMap();

    /**
     * Refresh Current node.
     *
     * @param slotsIds Current slots ids.
     */
    public static synchronized void refreshCurrentSlots(Set<Long> slotsIds) {
        CURRENT_SLOTS.clear();
        CURRENT_SLOTS.addAll(slotsIds);
    }

    /**
     * Refresh nodes.
     *
     * @param nodes nodes
     */
    public static synchronized void refreshNodeList(Map<Long, Node> nodes) {
        NODES_LIST.clear();
        NODES_LIST.putAll(nodes);
    }

    /**
     * Refresh app worker.
     *
     * @param workers workers
     */
    public static synchronized void refreshAppWorkers(Map<Long, List<WorkerDTO>> workers) {
        APP_WORKERS.clear();
        APP_WORKERS.putAll(workers);
    }

    /**
     * Refresh slots list.
     *
     * @param slotsList Slots list.
     */
    public static synchronized void refreshSlotsListMap(Map<Long, Long> slotsList) {
        SLOTS_LIST_MAP.clear();
        SLOTS_LIST_MAP.putAll(slotsList);
    }

    /**
     * Set cluster actor reference.
     *
     * @param clusterActorRef clusterActorRef
     */
    public static synchronized void setClusterActorRef(ActorRef clusterActorRef) {
        ClusterContext.clusterActorRef = clusterActorRef;
    }

    /**
     * Set current node.
     *
     * @param currentNode currentNode
     */
    public static synchronized void setCurrentNode(Node currentNode) {
        ClusterContext.currentNode = currentNode;
    }

    public static Map<Long, Long> getSlotsListMap() {
        return SLOTS_LIST_MAP;
    }

    public static Map<Long, List<WorkerDTO>> get() {
        return APP_WORKERS;
    }

    public static Map<Long, List<WorkerDTO>> getAppWorkers() {
        return APP_WORKERS;
    }

    /**
     * Return current slots.
     *
     * @return Set<Long>
     */
    public static Set<Long> getCurrentSlots() {
        return CURRENT_SLOTS;
    }


    /**
     * Return nodes.
     *
     * @return Map<Long, Node>
     */
    public static Map<Long, Node> getNodesList() {
        return NODES_LIST;
    }

    public static ActorRef getClusterActorRef() {
        return clusterActorRef;
    }

    /**
     * Return node.
     *
     * @return Node
     */
    public static Node getCurrentNode() {
        return currentNode;
    }
}
