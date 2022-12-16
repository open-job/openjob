package io.openjob.server.cluster.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.WorkerStartRequest;
import io.openjob.common.request.WorkerStopRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.ServerResponse;
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
public class WorkerActor extends BaseActor {

    private final WorkerService workerService;

    @Autowired
    public WorkerActor(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerStartRequest.class, this::workerStart)
                .match(WorkerStopRequest.class, this::workerStop)
                .build();
    }

    /**
     * Worker start
     *
     * @param workerStartRequest start request.
     */
    public void workerStart(WorkerStartRequest workerStartRequest) {
        this.workerService.workerStart(workerStartRequest);

        log.info("Worker register success! address={}", workerStartRequest.getAddress());
        getSender().tell(Result.success(new ServerResponse()), getSelf());
    }

    public void workerStop(WorkerStopRequest workerStopRequest) {
        this.workerService.workerStop(workerStopRequest);

        log.info("Worker stop success! address={}", workerStopRequest.getAddress());
        getSender().tell(Result.success(new ServerResponse()), getSelf());
    }
}
