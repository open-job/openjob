package io.openjob.worker.master;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.worker.dto.JobInstanceDTO;
import io.openjob.worker.entity.Task;
import io.openjob.worker.request.MasterStartContainerRequest;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class StandaloneTaskMaster extends BaseTaskMaster {
    public StandaloneTaskMaster(JobInstanceDTO jobInstanceDTO, ActorContext actorContext) {
        super(jobInstanceDTO, actorContext);
    }

    @Override
    public void submit() {
        // Create container and init status.
        MasterStartContainerRequest startRequest = this.wrapMasterStartContainerRequest();

        Task task = new Task();
        task.setJobId(startRequest.getJobId());
        task.setInstanceId(startRequest.getJobInstanceId());
        task.setCircleId(0L);
        task.setTaskId(startRequest.getTaskUniqueId());
        task.setTaskParentId("0");
        task.setTaskName("standalone");
        task.setStatus(TaskStatusEnum.INIT.getStatus());
        task.setWorkerAddress(this.localWorkerAddress);
        taskDAO.add(task);

        ActorSelection actorSelection = actorContext.actorSelection(this.localContainerPath);
        FutureUtil.mustAsk(actorSelection, startRequest, WorkerResponse.class, 3L);
    }

    @Override
    public void stop() {

    }
}
