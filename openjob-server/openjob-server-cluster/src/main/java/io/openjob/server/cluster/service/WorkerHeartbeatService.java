package io.openjob.server.cluster.service;

import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.response.ServerHeartbeatResponse;
import io.openjob.common.response.ServerHeartbeatSystemResponse;
import io.openjob.server.cluster.dto.WorkerHeartbeatReqDTO;
import io.openjob.server.cluster.dto.WorkerHeartbeatRespDTO;
import io.openjob.server.cluster.manager.WorkerHeartbeatManager;
import io.openjob.server.common.util.BeanMapperUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Service
@Log4j2
public class WorkerHeartbeatService {
    private final WorkerHeartbeatManager workerHeartbeatManager;

    @Autowired
    public WorkerHeartbeatService(WorkerHeartbeatManager workerHeartbeatManager) {
        this.workerHeartbeatManager = workerHeartbeatManager;
    }

    /**
     * Worker heartbeat
     *
     * @param heartbeatReq heartbeat request.
     */
    public ServerHeartbeatResponse workerHeartbeat(WorkerHeartbeatRequest heartbeatReq) {
        WorkerHeartbeatRespDTO workerHeartbeatRespDTO = this.workerHeartbeatManager.workerHeartbeat(BeanMapperUtil.map(heartbeatReq, WorkerHeartbeatReqDTO.class));

        // System response
        ServerHeartbeatSystemResponse systemResponse = new ServerHeartbeatSystemResponse();
        systemResponse.setClusterDelayVersion(workerHeartbeatRespDTO.getClusterDelayVersion());
        systemResponse.setClusterVersion(workerHeartbeatRespDTO.getClusterVersion());

        // Response
        ServerHeartbeatResponse serverHeartbeatResponse = new ServerHeartbeatResponse();
        serverHeartbeatResponse.setWorkerAddressList(workerHeartbeatRespDTO.getWorkerAddressList());
        serverHeartbeatResponse.setSystemResponse(systemResponse);
        return serverHeartbeatResponse;
    }
}
