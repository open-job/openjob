package io.openjob.server.cluster;

import akka.actor.AbstractActor;
import io.openjob.server.cluster.message.Ping;
import io.openjob.server.cluster.message.Pong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
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
