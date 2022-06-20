package io.openjob.server.event;

import io.openjob.server.cluster.ClusterNode;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ClusterNode clusterNode;

    public ApplicationReadyEventListener(ClusterNode clusterManager) {
        this.clusterNode = clusterManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Initialize cluster node.
        this.clusterNode.init();
    }
}
