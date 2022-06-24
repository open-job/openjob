package io.openjob.server.event;

import io.openjob.server.cluster.ClusterServer;
import io.openjob.server.scheduler.Scheduler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ClusterServer clusterNode;
    private final Scheduler scheduler;

    public ApplicationReadyEventListener(ClusterServer clusterManager, Scheduler scheduler) {
        this.clusterNode = clusterManager;
        this.scheduler = scheduler;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Initialize cluster node.
        this.clusterNode.init();

        // Start scheduler
        this.scheduler.start();
    }
}
