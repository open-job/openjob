package io.openjob.server.core;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import io.openjob.server.common.SpringExtensionProvider;
import io.openjob.server.common.constant.ActorConstant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class Server {
    private final ActorSystem actorSystem;

    @Autowired
    public Server(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    /**
     * Application ready to start server
     */
    public void start() {
        ActorRef serverActor = actorSystem.actorOf(SpringExtensionProvider.getProvider().get(actorSystem).Create("serverActor").withDispatcher(ActorConstant.SERVER_DISPATCHER), ActorConstant.SERVER_NAME);
        System.out.println(serverActor);
        // Register server

        // Refresh task sharding

        // Send message to cluster servers for refresh task sharding
    }
}
