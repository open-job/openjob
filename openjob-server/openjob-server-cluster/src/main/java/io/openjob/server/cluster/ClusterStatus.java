package io.openjob.server.cluster;

import akka.actor.ActorRef;
import com.google.common.collect.Maps;
import io.openjob.server.cluster.context.Node;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
public class ClusterStatus {

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
    private static final Set<Long> currentSlots = new HashSet<>();

    /**
     * Slots list map.
     * Key is slots id, Value is server id.
     */
    private static final Map<Long, Long> slotsListMap = Maps.newHashMap();

    /**
     * Cluster nodes.
     * Key is server id, Value is server node.
     */
    private static final Map<Long, Node> nodesList = Maps.newConcurrentMap();

    /**
     * Refresh Current node.
     *
     * @param slotsIds Current slots ids.
     */
    public static synchronized void refreshCurrentSlots(Set<Long> slotsIds) {
        currentSlots.addAll(slotsIds);
    }

    /**
     * Refresh nodes.
     *
     * @param nodes nodes
     */
    public static synchronized void refreshNodeList(Map<Long, Node> nodes) {
        nodesList.putAll(nodes);
    }

    /**
     * Refresh slots list.
     *
     * @param slotsList Slots list.
     */
    public static synchronized void refreshSlotsListMap(Map<Long, Long> slotsList) {
        slotsListMap.putAll(slotsList);
    }

    /**
     * Set cluster actor reference.
     *
     * @param clusterActorRef clusterActorRef
     */
    public static synchronized void setClusterActorRef(ActorRef clusterActorRef) {
        ClusterStatus.clusterActorRef = clusterActorRef;
    }

    /**
     * Set current node.
     *
     * @param currentNode currentNode
     */
    public static synchronized void setCurrentNode(Node currentNode) {
        ClusterStatus.currentNode = currentNode;
    }


    /**
     * Return current slots.
     *
     * @return Set<Long>
     */
    public static Set<Long> getCurrentSlots() {
        return currentSlots;
    }

    /**
     * Return nodes.
     *
     * @return Map<Long, Node>
     */
    public static Map<Long, Node> getNodesList() {
        return nodesList;
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
