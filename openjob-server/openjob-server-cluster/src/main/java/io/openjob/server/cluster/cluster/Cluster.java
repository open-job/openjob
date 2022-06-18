package io.openjob.server.cluster.cluster;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class Cluster {
    private static volatile Map<Long, Slots> slotsMap = new HashMap<>();
    private static volatile Map<Long, Node> nodesMap = new HashMap<>();

    public static Map<Long, Node> getNodesMap() {
        return nodesMap;
    }

    public static synchronized void refreshNodes(Map<Long, Node> nodes) {
        nodesMap = nodes;
    }
}
