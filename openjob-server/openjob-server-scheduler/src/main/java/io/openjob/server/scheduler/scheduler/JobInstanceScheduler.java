package io.openjob.server.scheduler.scheduler;

import akka.actor.ActorSelection;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.request.ServerInstanceTaskChildListPullRequest;
import io.openjob.common.request.ServerInstanceTaskListPullRequest;
import io.openjob.common.request.ServerStopJobInstanceRequest;
import io.openjob.common.response.WorkerInstanceTaskChildListPullResponse;
import io.openjob.common.response.WorkerInstanceTaskListPullResponse;
import io.openjob.common.response.WorkerResponse;
import io.openjob.common.util.FutureUtil;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.common.util.ServerUtil;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.entity.Worker;
import io.openjob.server.scheduler.dto.JobInstanceStopRequestDTO;
import io.openjob.server.scheduler.dto.JobInstanceStopResponseDTO;
import io.openjob.server.scheduler.dto.TaskChildPullRequestDTO;
import io.openjob.server.scheduler.dto.TaskChildPullResponseDTO;
import io.openjob.server.scheduler.dto.TaskListPullRequestDTO;
import io.openjob.server.scheduler.dto.TaskListPullResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
@Component
public class JobInstanceScheduler {
    private final JobInstanceDAO jobInstanceDAO;
    private final WorkerDAO workerDAO;

    @Autowired
    public JobInstanceScheduler(JobInstanceDAO jobInstanceDAO, WorkerDAO workerDAO) {
        this.jobInstanceDAO = jobInstanceDAO;
        this.workerDAO = workerDAO;
    }

    /**
     * Pull task list
     *
     * @param request request
     * @return List
     */
    public List<TaskListPullResponseDTO> pullTaskList(TaskListPullRequestDTO request) {
        JobInstance jobInstance = this.jobInstanceDAO.getById(request.getJobInstanceId());

        try {
            ServerInstanceTaskListPullRequest pullRequest = new ServerInstanceTaskListPullRequest();
            pullRequest.setJobInstanceId(request.getJobInstanceId());
            pullRequest.setDispatchVersion(jobInstance.getDispatchVersion());
            pullRequest.setCircleId(request.getCircleId());

            ActorSelection masterActor = ServerUtil.getWorkerTaskMasterActor(jobInstance.getWorkerAddress());
            WorkerInstanceTaskListPullResponse response = FutureUtil.mustAsk(masterActor, pullRequest, WorkerInstanceTaskListPullResponse.class, 3000L);
            return Optional.ofNullable(response.getTaskList()).orElseGet(ArrayList::new)
                    .stream().map(t -> BeanMapperUtil.map(t, TaskListPullResponseDTO.class)).collect(Collectors.toList());
        } catch (Throwable ex) {
            log.warn("Pull instance task list failed!", ex);
            return new ArrayList<>();
        }
    }

    /**
     * Pull child task
     *
     * @param request request
     * @return List
     */
    public List<TaskChildPullResponseDTO> pullChildTask(TaskChildPullRequestDTO request) {
        JobInstance jobInstance = this.jobInstanceDAO.getById(request.getJobInstanceId());

        try {
            ServerInstanceTaskChildListPullRequest pullRequest = new ServerInstanceTaskChildListPullRequest();
            pullRequest.setTaskId(request.getTaskId());
            pullRequest.setJobInstanceId(request.getJobInstanceId());
            pullRequest.setDispatchVersion(jobInstance.getDispatchVersion());
            pullRequest.setCircleId(request.getCircleId());

            ActorSelection masterActor = ServerUtil.getWorkerTaskMasterActor(jobInstance.getWorkerAddress());
            WorkerInstanceTaskChildListPullResponse response = FutureUtil.mustAsk(masterActor, pullRequest, WorkerInstanceTaskChildListPullResponse.class, 1000L);
            return Optional.ofNullable(response.getTaskList()).orElseGet(ArrayList::new)
                    .stream().map(t -> BeanMapperUtil.map(t, TaskChildPullResponseDTO.class)).collect(Collectors.toList());
        } catch (Throwable ex) {
            log.warn("Pull instance child task list failed!", ex);
            return new ArrayList<>();
        }
    }

    /**
     * Stop
     *
     * @param stopRequest stopRequest
     * @return JobInstanceStopResponseDTO
     */
    public JobInstanceStopResponseDTO stop(JobInstanceStopRequestDTO stopRequest) {
        JobInstanceStopResponseDTO jobInstanceStopResponseDTO = new JobInstanceStopResponseDTO();
        JobInstance jobInstance = this.jobInstanceDAO.getById(stopRequest.getJobInstanceId());
        if (Objects.isNull(jobInstance)) {
            throw new RuntimeException("Job instance is not exist! id=" + stopRequest.getJobInstanceId());
        }

        // Not running or empty address.
        if (!InstanceStatusEnum.isRunning(jobInstance.getStatus()) || StringUtils.isEmpty(jobInstance.getWorkerAddress())) {
            jobInstanceStopResponseDTO.setType(1);
            return jobInstanceStopResponseDTO;
        }

        //  Worker offline
        Worker byAddress = this.workerDAO.getByAddress(jobInstance.getWorkerAddress());
        if (WorkerStatusEnum.OFFLINE.getStatus().equals(byAddress.getStatus())) {
            jobInstanceStopResponseDTO.setType(0);
            return jobInstanceStopResponseDTO;
        }

        try {
            ServerStopJobInstanceRequest serverStopJobInstanceRequest = new ServerStopJobInstanceRequest();
            serverStopJobInstanceRequest.setJobId(jobInstance.getJobId());
            serverStopJobInstanceRequest.setJobInstanceId(jobInstance.getId());
            ActorSelection masterActor = ServerUtil.getWorkerTaskMasterActor(jobInstance.getWorkerAddress());
            FutureUtil.mustAsk(masterActor, serverStopJobInstanceRequest, WorkerResponse.class, 1000L);
        } catch (Throwable throwable) {
            log.error("Job instance exception!", throwable);
            throw new RuntimeException("Job instance exception! message=" + throwable.getMessage());
        }

        jobInstanceStopResponseDTO.setType(0);
        return jobInstanceStopResponseDTO;
    }
}
