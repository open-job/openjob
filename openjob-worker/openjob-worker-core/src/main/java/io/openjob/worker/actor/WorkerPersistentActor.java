package io.openjob.worker.actor;

import akka.persistence.AbstractPersistentActorWithAtLeastOnceDelivery;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerPersistentActor extends AbstractPersistentActorWithAtLeastOnceDelivery {
    private final Integer id;

    public WorkerPersistentActor(Integer id) {
        this.id = id;
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder()
                .build();
    }

    @Override
    public Receive createReceive() {
        System.out.println(Thread.currentThread().getId());
        return receiveBuilder()
                .build();
    }

    @Override
    public String persistenceId() {
        return String.format("persistence-id-%d", id);
    }
}
