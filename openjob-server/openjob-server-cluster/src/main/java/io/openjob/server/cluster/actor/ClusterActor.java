package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.server.cluster.cluster.Cluster;
import io.openjob.server.cluster.cluster.Node;
import io.openjob.server.cluster.message.Join;
import io.openjob.server.cluster.message.Ping;
import io.openjob.server.cluster.message.Pong;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
@Log4j2
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClusterActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Ping.class, this::receivePing)
                .match(Join.class, this::receiveJoin)
                .matchAny(obj -> System.out.println("akk mesage tst"))
                .build();
    }

    public void receivePing(Ping ping) {
        getSender().tell(new Pong(), getSelf());
    }

    public void receiveJoin(Join join) {
        Node node = new Node();
        node.setAkkaAddress(join.getAkkaAddress());
        node.setIp(join.getIp());
        node.setServerId(join.getServerId());

        Cluster.addNode(join.getServerId(), node);

        log.info("join node success!");
    }
}
