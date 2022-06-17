package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.server.cluster.message.Ping;
import io.openjob.server.cluster.message.Pong;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClusterActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Ping.class, this::receivePing)
                .build();
    }

    public void receivePing(Ping ping) {
        getSender().tell(new Pong(), getSelf());
    }
}