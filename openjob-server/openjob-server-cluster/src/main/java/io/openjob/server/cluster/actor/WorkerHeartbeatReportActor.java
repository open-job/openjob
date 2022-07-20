package io.openjob.server.cluster.actor;

import akka.actor.AbstractActor;
import io.openjob.common.request.WorkerHeartbeatReportRequest;
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
public class WorkerHeartbeatReportActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerHeartbeatReportRequest.class, this::workerHeartbeatReport)
                .build();
    }

    public void workerHeartbeatReport(WorkerHeartbeatReportRequest workerHeartbeatReportRequest) {
        System.out.println(workerHeartbeatReportRequest);
    }
}
