package io.openjob.server.cluster.util;

import io.openjob.server.cluster.ClusterStatus;
import io.openjob.server.cluster.context.Node;
import io.openjob.server.repository.entity.JobSlots;
import io.openjob.server.repository.entity.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ClusterStatusUtil {
    public static void refreshNodes(List<Server> servers) {
        Map<Long, Node> nodes = new HashMap<>(16);
        servers.forEach(s -> {
            Node node = new Node();
            node.setServerId(s.getId());
            node.setIp(s.getIp());
            node.setAkkaAddress(s.getAkkaAddress());
            nodes.put(s.getId(), node);
        });

        ClusterStatus.refreshNodeList(nodes);
    }

    public static void refreshSlotsListMap(List<JobSlots> jobSlots) {
        ClusterStatus.refreshSlotsListMap(jobSlots.stream().collect(Collectors.toMap(JobSlots::getId, JobSlots::getServerId)));
    }
}
