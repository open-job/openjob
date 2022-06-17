package io.openjob.server.event;

import io.openjob.server.cluster.ClusterManager;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ClusterManager clusterManager;

    public ApplicationReadyEventListener(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Init cluster manager
        this.clusterManager.init();

        // Init worker manager
    }
}
