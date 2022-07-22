package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
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
public class JobInstanceReportActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }
}
