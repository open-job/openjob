package io.openjob.server.cluster.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.WorkerJobInstanceStatusRequest;
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
public class WorkerJobInstanceActor extends BaseActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerJobInstanceStatusRequest.class, this::handleStatus)
                .build();
    }

    public void handleStatus(WorkerJobInstanceStatusRequest statusRequest) {

    }
}
