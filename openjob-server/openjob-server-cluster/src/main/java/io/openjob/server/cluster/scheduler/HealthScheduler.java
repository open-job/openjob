package io.openjob.server.cluster.scheduler;

import com.google.common.primitives.Booleans;
import io.openjob.server.cluster.cluster.Cluster;
import io.openjob.server.cluster.cluster.Node;
import io.openjob.server.cluster.message.Ping;
import io.openjob.server.common.util.AkkaUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class HealthScheduler {
    @Scheduled(fixedRate = 3 * 1000L)
    public void healthCheck() {
//        check();
    }

    private void check() {
        Map<Long, Node> nodesMap = Cluster.getNodesMap();
        List<Long> serverIds = new ArrayList<>();
        Long currentServerId = Cluster.getCurrentNode().getServerId();
        nodesMap.forEach((id, n) -> {
            if (currentServerId != id) {
                serverIds.add(id);
            }
        });

        serverIds.forEach(serverId -> {
            Node node = nodesMap.get(serverId);
            boolean success = false;
            try {
                AkkaUtil.clusterAsk(node.getAkkaAddress(), new Ping());
                success = true;
            } catch (Exception e) {

            }

            if (!success) {

            }
        });
    }
}
