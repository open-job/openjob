package io.openjob.server.core;

import akka.actor.ActorSystem;
import akka.actor.Props;
import io.openjob.server.cluster.ClusterActor;
import io.openjob.server.core.actor.ServerActor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class ServerActorSystem implements InitializingBean {
    private ActorSystem actorSystem;

    @Override
    public void afterPropertiesSet() {
        this.actorSystem = ActorSystem.create("open-server");
        this.actorSystem.actorOf(Props.create(ClusterActor.class), "cluster");
        this.actorSystem.actorOf(Props.create(ServerActor.class), "server");
    }
}
