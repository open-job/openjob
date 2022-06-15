package io.openjob.server.core.actor;

import akka.actor.AbstractActor;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ServerActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
