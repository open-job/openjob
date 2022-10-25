package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.ServerResponse;
import io.openjob.server.cluster.service.WorkerHeartbeatService;
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
public class WorkerHeartbeatActor extends BaseActor {

    private final WorkerHeartbeatService workerHeartbeatService;

    @Autowired
    public WorkerHeartbeatActor(WorkerHeartbeatService workerHeartbeatService) {
        this.workerHeartbeatService = workerHeartbeatService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerHeartbeatRequest.class, this::workerHeartbeat)
                .build();
    }

    /**
     * Worker heartbeat request.
     *
     * @param workerHeartbeatRequest heartbeat request.
     */
    public void workerHeartbeat(WorkerHeartbeatRequest workerHeartbeatRequest) {
        workerHeartbeatService.workerHeartbeat(workerHeartbeatRequest);
        log.info("Worker({}) heartbeat success!", workerHeartbeatRequest.getAddress());

        getSender().tell(Result.success(new ServerResponse()), getSelf());
    }
}
