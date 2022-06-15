package io.openjob.server.core.event;

import io.openjob.server.core.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    private final Server server;

    @Autowired
    public ApplicationReadyEventListener(Server server) {
        this.server = server;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.server.start();
    }
}
