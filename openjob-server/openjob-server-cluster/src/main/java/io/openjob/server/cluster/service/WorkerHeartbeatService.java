package io.openjob.server.cluster.service;

import io.openjob.common.SpringContext;
import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.response.ServerHeartbeatResponse;
import io.openjob.common.response.ServerHeartbeatSystemResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.common.util.LogUtil;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
@Log4j2
public class WorkerHeartbeatService {
    private final WorkerDAO workerDAO;
    private final JobInstanceDAO jobInstanceDAO;

    @Autowired
    public WorkerHeartbeatService(WorkerDAO workerDAO, JobInstanceDAO jobInstanceDAO) {
        this.workerDAO = workerDAO;
        this.jobInstanceDAO = jobInstanceDAO;
    }

    /**
     * Worker heartbeat
     *
     * @param heartbeatReq heartbeat request.
     */
    public ServerHeartbeatResponse workerHeartbeat(WorkerHeartbeatRequest heartbeatReq) {
        Worker worker = this.workerDAO.getByAddress(heartbeatReq.getAddress());
        if (Objects.isNull(worker)) {
            LogUtil.logAndThrow(String.format("worker(%s) do not exist!", heartbeatReq.getAddress()));
        }

        // Do heartbeat.
        SpringContext.getBean(this.getClass()).doHeartbeat(heartbeatReq, worker);

        ServerHeartbeatResponse response = new ServerHeartbeatResponse();
        ServerHeartbeatSystemResponse systemResponse = new ServerHeartbeatSystemResponse();

        // System information.
        SystemDTO system = ClusterContext.getSystem();
        systemResponse.setClusterVersion(system.getClusterVersion());
        systemResponse.setClusterDelayVersion(system.getClusterDelayVersion());
        response.setSystemResponse(systemResponse);

        // Online workers  and exclude start worker.
        Set<String> onlineWorkers = ClusterUtil.getOnlineWorkers(heartbeatReq.getAppId());
        response.setWorkerAddressList(onlineWorkers);
        return response;
    }

    /**
     * Do heartbeat
     *
     * @param workerHeartbeatReq workerHeartbeatReq
     * @param worker             worker
     */
    @Transactional(rollbackFor = Exception.class)
    public void doHeartbeat(WorkerHeartbeatRequest workerHeartbeatReq, Worker worker) {
        worker.setLastHeartbeatTime(DateUtil.timestamp());
        this.workerDAO.save(worker);

        List<Long> instanceIds = workerHeartbeatReq.getRunningJobInstanceIds();
        if (!CollectionUtils.isEmpty(instanceIds)) {
            this.jobInstanceDAO.updateLastReportTimeByIds(instanceIds, DateUtil.timestamp());
        }
    }
}
