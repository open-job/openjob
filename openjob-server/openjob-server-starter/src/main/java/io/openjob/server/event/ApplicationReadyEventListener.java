package io.openjob.server.event;

import io.openjob.server.cluster.ClusterServer;
import io.openjob.server.scheduler.Scheduler;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ClusterServer clusterServer;

    public ApplicationReadyEventListener(ClusterServer clusterManager) {
        this.clusterServer = clusterManager;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        // Start server.
        this.clusterServer.start();

        // Start scheduler.
        Scheduler.start();
    }
}
