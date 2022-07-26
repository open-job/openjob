package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.dto.JobInstanceDTO;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class BaseTaskMaster implements TaskMaster {

    protected AtomicLong taskIdGenerator = new AtomicLong(0);
    protected JobInstanceDTO jobInstanceDTO;
    protected ActorContext actorContext;
    protected String localWorkerAddress;
    protected String localContainerPath;

    public BaseTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        this.jobInstanceDTO = jobInstanceDTO;
        this.actorContext = actorContext;
        this.localWorkerAddress = actorContext.provider().getDefaultAddress().hostPort();
        this.localContainerPath = actorContext.provider().getDefaultAddress().toString() + WorkerAkkaConstant.PATH_TASK_CONTAINER;
    }

    protected Long acquireTaskId() {
        return taskIdGenerator.getAndIncrement();
    }
}
