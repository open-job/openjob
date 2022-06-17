package io.openjob.server.common.actor;

import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SpringExtension implements Extension {
    private ApplicationContext context;

    public void init(ApplicationContext context) {
        this.context = context;
    }

    public Props Create(String beanName) {
        return Props.create(ActorProducer.class, this.context, beanName);
    }
}
