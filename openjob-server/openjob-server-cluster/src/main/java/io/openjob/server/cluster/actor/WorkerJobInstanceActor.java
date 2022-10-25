package io.openjob.server.cluster.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.WorkerJobInstanceLogRequest;
import io.openjob.common.request.WorkerJobInstanceStatusRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.ServerResponse;
import io.openjob.server.cluster.service.JobInstanceService;
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
public class WorkerJobInstanceActor extends BaseActor {
    private final JobInstanceService instanceService;

    @Autowired
    public WorkerJobInstanceActor(JobInstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerJobInstanceStatusRequest.class, this::handleStatus)
                .match(WorkerJobInstanceLogRequest.class, this::handleInstanceLog)
                .build();
    }

    /**
     * Handle status.
     *
     * @param statusRequest status request.
     */
    public void handleStatus(WorkerJobInstanceStatusRequest statusRequest) {
        // Handle instance status
        this.instanceService.handleInstanceStatus(statusRequest);

        // Response
        ServerResponse serverResponse = new ServerResponse(statusRequest.getDeliveryId());
        getSender().tell(Result.success(serverResponse), getSelf());
    }

    /**
     * Handle instance log.
     *
     * @param logRequest log request.
     */
    public void handleInstanceLog(WorkerJobInstanceLogRequest logRequest) {
        // Handle instance log.
        this.instanceService.handleInstanceLog(logRequest);

        // Response
        ServerResponse serverResponse = new ServerResponse(logRequest.getDeliveryId());
        getSender().tell(Result.success(serverResponse), getSelf());
    }
}
