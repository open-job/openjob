package io.openjob.worker.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class WorkerPersistentRoutingActor extends UntypedAbstractActor {
    private final Integer size;
    private final List<ActorRef> actors = new ArrayList<>();

    public WorkerPersistentRoutingActor(Integer size) {
        this.size = size;

        for (int i = 0; i < size; i++) {
            ActorRef actorRef = getContext().actorOf(Props.create(WorkerPersistentActor.class, i));
            actors.add(actorRef);
        }
    }

    @Override
    public void onReceive(Object message) {
        int index = ThreadLocalRandom.current().nextInt(this.size);
        actors.get(index).forward(message, getContext());
    }
}
