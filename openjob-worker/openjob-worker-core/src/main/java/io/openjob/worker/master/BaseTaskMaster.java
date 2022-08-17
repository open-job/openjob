package io.openjob.worker.master;

import akka.actor.ActorContext;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.worker.constant.WorkerAkkaConstant;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.request.MasterStartContainerRequest;
import io.openjob.worker.util.WorkerUtil;

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

    /**
     * @param jobInstanceDTO
     * @param actorContext
     */
    public BaseTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        this.jobInstanceDTO = jobInstanceDTO;
        this.actorContext = actorContext;
        this.localWorkerAddress = actorContext.provider().addressString();
        this.localContainerPath = actorContext.provider().getDefaultAddress().toString() + WorkerAkkaConstant.PATH_TASK_CONTAINER;
    }

    protected void init() {

    }

    protected Long acquireTaskId() {
        return taskIdGenerator.getAndIncrement();
    }

    protected MasterStartContainerRequest wrapMasterStartContainerRequest() {
        MasterStartContainerRequest startReq = new MasterStartContainerRequest();
        startReq.setJobId(this.jobInstanceDTO.getJobId());
        startReq.setJobInstanceId(this.jobInstanceDTO.getJobInstanceId());
        startReq.setTaskId(this.acquireTaskId());
        startReq.setJobParams(this.jobInstanceDTO.getJobParams());
        startReq.setExecuteType(this.jobInstanceDTO.getExecuteType());
        startReq.setWorkflowId(this.jobInstanceDTO.getWorkflowId());
        startReq.setProcessorType(this.jobInstanceDTO.getProcessorType());
        startReq.setProcessorInfo(this.jobInstanceDTO.getProcessorInfo());
        startReq.setFailRetryInterval(this.jobInstanceDTO.getFailRetryInterval());
        startReq.setFailRetryTimes(this.jobInstanceDTO.getFailRetryTimes());
        startReq.setTimeExpression(this.jobInstanceDTO.getTimeExpression());
        startReq.setTimeExpressionType(this.jobInstanceDTO.getTimeExpressionType());
        startReq.setConcurrency(this.jobInstanceDTO.getConcurrency());
        startReq.setMasterAkkaPath(this.localContainerPath);
        startReq.setWorkerAddresses(this.jobInstanceDTO.getWorkerAddresses());
        startReq.setMasterAkkaPath(String.format("%s%s", this.localWorkerAddress, AkkaConstant.WORKER_PATH_TASK_MASTER));
        return startReq;
    }
}
