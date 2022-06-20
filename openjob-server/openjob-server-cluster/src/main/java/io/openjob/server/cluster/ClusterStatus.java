package io.openjob.server.cluster;

import com.google.common.collect.Maps;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.cluster.context.Slots;

import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ClusterStatus {
    private static volatile Node currentNode;
    private static volatile Map<Long, Slots> slotsMap = Maps.newConcurrentMap();
    private static volatile Map<Long, Node> nodesMap = Maps.newConcurrentMap();

    public static Map<Long, Node> getNodesMap() {
        return nodesMap;
    }

    public static synchronized void refreshNodes(Map<Long, Node> nodes) {
        nodesMap = nodes;
    }

    public static synchronized void addNode(Long serverId, Node node) {
        nodesMap.put(serverId, node);
    }

    public static synchronized void setCurrentNode(Node currentNode) {
        ClusterStatus.currentNode = currentNode;
    }

    public static Node getCurrentNode() {
        return currentNode;
    }
}
