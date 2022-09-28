package io.openjob.server.cluster.actor;

import io.openjob.common.actor.BaseActor;
import io.openjob.common.request.WorkerJobInstanceTaskLogRequest;
import io.openjob.common.response.Result;
import io.openjob.common.response.ServerResponse;
import io.openjob.server.cluster.service.JobInstanceTaskLogService;
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
public class WorkerJobInstanceTaskLogActor extends BaseActor {

    private final JobInstanceTaskLogService jobInstanceTaskLogService;

    @Autowired
    public WorkerJobInstanceTaskLogActor(JobInstanceTaskLogService jobInstanceTaskLogService) {
        this.jobInstanceTaskLogService = jobInstanceTaskLogService;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WorkerJobInstanceTaskLogRequest.class, this::handleLog)
                .build();
    }

    public void handleLog(WorkerJobInstanceTaskLogRequest logRequest) {
        this.jobInstanceTaskLogService.handleInstanceTaskLog(logRequest);
        getSender().tell(Result.success(new ServerResponse()), getSelf());
    }
}
