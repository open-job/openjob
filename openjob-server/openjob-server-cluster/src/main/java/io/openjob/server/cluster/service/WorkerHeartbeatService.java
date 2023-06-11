package io.openjob.server.cluster.service;

import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.response.ServerHeartbeatResponse;
import io.openjob.common.response.ServerHeartbeatSystemResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.executor.WorkerHeartbeatExecutor;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Service
@Log4j2
public class WorkerHeartbeatService {
    private final WorkerDAO workerDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final WorkerHeartbeatExecutor workerHeartbeatExecutor;

    @Autowired
    public WorkerHeartbeatService(WorkerDAO workerDAO,
                                  JobInstanceDAO jobInstanceDAO,
                                  WorkerHeartbeatExecutor workerHeartbeatExecutor) {
        this.workerDAO = workerDAO;
        this.jobInstanceDAO = jobInstanceDAO;
        this.workerHeartbeatExecutor = workerHeartbeatExecutor;
    }

    /**
     * Worker heartbeat
     *
     * @param heartbeatReq heartbeat request.
     */
    public ServerHeartbeatResponse workerHeartbeat(WorkerHeartbeatRequest heartbeatReq) {
        //Submit request
        this.workerHeartbeatExecutor.submit(heartbeatReq);

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
     * Batch heartbeat
     *
     * @param requests requests
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchHeartbeat(List<WorkerHeartbeatRequest> requests) {
        Long timestamp = DateUtil.timestamp();
        Set<String> addressList = new HashSet<>();
        Set<Long> instanceIds = new HashSet<>();

        // Merge requests
        requests.forEach(r -> {
            addressList.add(r.getAddress());
            if (!CollectionUtils.isEmpty(r.getRunningJobInstanceIds())) {
                instanceIds.addAll(r.getRunningJobInstanceIds());
            }
        });

        // Worker heartbeat
        if (!CollectionUtils.isEmpty(addressList)) {
            this.workerDAO.updateLastHeartbeatTimeByAddresses(new ArrayList<>(addressList), timestamp);
        }

        // Instance last report time.
        if (!CollectionUtils.isEmpty(instanceIds)) {
            this.jobInstanceDAO.updateLastReportTimeByIds(new ArrayList<>(instanceIds), timestamp);
        }
    }
}
