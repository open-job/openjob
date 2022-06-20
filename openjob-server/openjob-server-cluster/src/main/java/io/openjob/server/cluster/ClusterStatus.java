package io.openjob.server.cluster;

import akka.actor.ActorRef;
import com.google.common.collect.Maps;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.context.Slots;

import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ClusterStatus {

    /**
     * Cluster actor reference.
     */
    private static volatile ActorRef clusterActorRef;

    /**
     * Current node.
     */
    private static volatile Node currentNode;

    /**
     * Job instance slots.
     */
    private static volatile Map<Long, Slots> slotsMap = Maps.newConcurrentMap();

    /**
     * Cluster nodes.
     */
    private static volatile Map<Long, Node> nodesMap = Maps.newConcurrentMap();

    /**
     * Refresh slots.
     *
     * @param slots slots
     */
    public static synchronized void refreshSlots(Map<Long, Slots> slots) {
        slotsMap = slots;
    }

    /**
     * Refresh nodes.
     *
     * @param nodes nodes
     */
    public static synchronized void refreshNodes(Map<Long, Node> nodes) {
        nodesMap = nodes;
    }

    /**
     * Append node.
     *
     * @param serverId serverId
     * @param node     node
     */
    public static synchronized void appendNode(Long serverId, Node node) {
        nodesMap.put(serverId, node);
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
     * Return nodes.
     *
     * @return Map<Long, Node>
     */
    public static Map<Long, Node> getNodesMap() {
        return nodesMap;
    }

    /**
     * Return job instance slots.
     *
     * @return Map<Long, Slots>
     */
    public static Map<Long, Slots> getSlotsMap() {
        return slotsMap;
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
