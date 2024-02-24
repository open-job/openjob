package io.openjob.server.cluster.manager;

import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.response.ServerHeartbeatResponse;
import io.openjob.common.response.ServerHeartbeatSystemResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.dto.WorkerHeartbeatReqDTO;
import io.openjob.server.cluster.dto.WorkerHeartbeatRespDTO;
import io.openjob.server.cluster.executor.WorkerHeartbeatExecutor;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.8
 */
@Slf4j
@Component
public class WorkerHeartbeatManager {
    private final WorkerDAO workerDAO;
    private final JobInstanceDAO jobInstanceDAO;
    private final WorkerHeartbeatExecutor workerHeartbeatExecutor;

    @Autowired
    public WorkerHeartbeatManager(WorkerDAO workerDAO,
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
    public WorkerHeartbeatRespDTO workerHeartbeat(WorkerHeartbeatReqDTO heartbeatReq) {
        //Submit request
        this.workerHeartbeatExecutor.submit(heartbeatReq);


        // System information.
        SystemDTO system = ClusterContext.getSystem();

        // Online workers  and exclude start worker.
        Set<String> onlineWorkers = ClusterUtil.getOnlineWorkers(heartbeatReq.getAppId());

        WorkerHeartbeatRespDTO respDTO = new WorkerHeartbeatRespDTO();
        respDTO.setClusterVersion(system.getClusterVersion());
        respDTO.setClusterDelayVersion(system.getClusterDelayVersion());
        respDTO.setWorkerAddressList(onlineWorkers);
        return respDTO;
    }

    /**
     * Batch heartbeat
     *
     * @param requests requests
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchHeartbeat(List<WorkerHeartbeatReqDTO> requests) {
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
