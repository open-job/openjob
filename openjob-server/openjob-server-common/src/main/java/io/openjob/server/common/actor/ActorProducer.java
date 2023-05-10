package io.openjob.server.common.actor;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class ActorProducer implements IndirectActorProducer {
    private final ApplicationContext context;
    private final String beanName;

    public ActorProducer(ApplicationContext applicationContext, String beanName) {
        this.context = applicationContext;
        this.beanName = beanName;
    }

    @Override
    public Actor produce() {
        return (Actor) context.getBean(beanName);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) context.getType(beanName);
    }
}
