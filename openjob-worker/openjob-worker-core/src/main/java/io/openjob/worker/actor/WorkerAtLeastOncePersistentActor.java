package io.openjob.worker.actor;

import akka.persistence.AbstractPersistentActorWithAtLeastOnceDelivery;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerAtLeastOncePersistentActor extends AbstractPersistentActorWithAtLeastOnceDelivery {
    @Override
    public Receive createReceiveRecover() {
        return null;
    }

    @Override
    public Receive createReceive() {
        return null;
    }

    @Override
    public String persistenceId() {
        return null;
    }
}
