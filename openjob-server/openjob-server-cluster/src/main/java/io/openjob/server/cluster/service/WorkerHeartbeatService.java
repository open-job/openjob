package io.openjob.server.cluster.service;

import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.response.ServerHeartbeatResponse;
import io.openjob.common.response.ServerHeartbeatSystemResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.common.util.LogUtil;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
@Log4j2
public class WorkerHeartbeatService {
    private final WorkerDAO workerDAO;

    @Autowired
    public WorkerHeartbeatService(WorkerDAO workerDAO) {
        this.workerDAO = workerDAO;
    }

    /**
     * Worker heartbeat
     *
     * @param workerHeartbeatReq heartbeat request.
     */
    public ServerHeartbeatResponse workerHeartbeat(WorkerHeartbeatRequest workerHeartbeatReq) {
        Worker worker = workerDAO.getByAddress(workerHeartbeatReq.getAddress());
        if (Objects.isNull(worker)) {
            LogUtil.logAndThrow(String.format("worker(%s) do not exist!", workerHeartbeatReq.getAddress()));
        }

        worker.setLastHeartbeatTime(DateUtil.timestamp());
        workerDAO.save(worker);

        ServerHeartbeatResponse response = new ServerHeartbeatResponse();
        ServerHeartbeatSystemResponse systemResponse = new ServerHeartbeatSystemResponse();

        SystemDTO system = ClusterContext.getSystem();
        systemResponse.setClusterVersion(system.getClusterVersion());
        systemResponse.setClusterDelayVersion(system.getClusterDelayVersion());
        response.setSystemResponse(systemResponse);
        return response;
    }
}
