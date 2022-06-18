package io.openjob.server.cluster.cluster;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class Cluster {
    private static volatile Map<Long, Slots> slotsMap = Maps.newConcurrentMap();
    private static volatile Map<Long, Node> nodesMap = Maps.newConcurrentMap();

    public static Map<Long, Node> getNodesMap() {
        return nodesMap;
    }

    public static synchronized void refreshNodes(Map<Long, Node> nodes) {
        nodesMap = nodes;
    }

    public static synchronized void addNode(Long id, Node node) {
        nodesMap.put(id, node);
    }
}
