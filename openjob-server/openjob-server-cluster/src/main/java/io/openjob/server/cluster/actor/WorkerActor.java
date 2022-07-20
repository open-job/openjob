package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.common.request.WorkerStartRequest;
import io.openjob.common.request.WorkerStopRequest;
import io.openjob.server.cluster.service.WorkerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WorkerActor extends AbstractActor {

    private final WorkerService workerService;

    @Autowired
    public WorkerActor(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerStartRequest.class, this.workerService::workerStart)
                .match(WorkerStopRequest.class, this.workerService::workerStop)
                .build();
    }
}
