package io.openjob.server.cluster.service;

import com.google.common.collect.Lists;
import io.openjob.common.request.WorkerStartRequest;
import io.openjob.common.request.WorkerStopRequest;
import io.openjob.common.response.ServerWorkerStartResponse;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.autoconfigure.ClusterProperties;
import io.openjob.server.cluster.dto.WorkerStartReqDTO;
import io.openjob.server.cluster.dto.WorkerStartRespDTO;
import io.openjob.server.cluster.dto.WorkerStopReqDTO;
import io.openjob.server.cluster.manager.WorkerManager;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Service
@Log4j2
public class WorkerService {
    private final WorkerDAO workerDAO;
    private final ClusterProperties clusterProperties;
    private final WorkerManager workerManager;

    @Autowired
    public WorkerService(WorkerDAO workerDAO, ClusterProperties clusterProperties, WorkerManager workerManager) {
        this.workerDAO = workerDAO;
        this.clusterProperties = clusterProperties;
        this.workerManager = workerManager;
    }

    /**
     * Worker start
     *
     * @param startRequest start request.
     */
    public ServerWorkerStartResponse workerStart(WorkerStartRequest startRequest) {
        WorkerStartReqDTO reqDTO = BeanMapperUtil.map(startRequest, WorkerStartReqDTO.class);
        WorkerStartRespDTO workerStartRespDTO = this.workerManager.workerStart(reqDTO);
        return BeanMapperUtil.map(workerStartRespDTO, ServerWorkerStartResponse.class);
    }

    /**
     * Worker stop
     *
     * @param stopReq stop request.
     */
    public void workerStop(WorkerStopRequest stopReq) {
        this.workerManager.workerStop(BeanMapperUtil.map(stopReq, WorkerStopReqDTO.class));
    }

    /**
     * Worker check
     */
    public void workerCheck() {
        Set<Long> currentSlots = ClusterContext.getCurrentSlots();

        // Query slot ids.
        List<Long> querySlotIds = Lists.newArrayList();
        currentSlots.forEach(id -> {
            if (id <= ClusterContext.getSystem().getWorkerSupervisorSlot()) {
                querySlotIds.add(id);
            }
        });

        // Query workers
        Map<Integer, List<Worker>> workerMap = this.workerDAO.listAllWorkersBySlotIds(querySlotIds)
                .stream()
                .collect(Collectors.groupingBy(Worker::getStatus));

        // New join worker
        long onlinePos = DateUtil.timestamp() - this.clusterProperties.getWorker().getOnlinePeriod();
        List<Worker> offlineWorkers = Optional.ofNullable(workerMap.get(WorkerStatusEnum.OFFLINE.getStatus())).orElseGet(ArrayList::new);
        offlineWorkers.forEach(w -> {
            // Join worker
            // Ignore just off the line
            if (w.getUpdateTime() < onlinePos && w.getLastHeartbeatTime() > onlinePos) {
                WorkerStartRequest workerStartRequest = new WorkerStartRequest();
                workerStartRequest.setAddress(w.getAddress());
                workerStartRequest.setAppName(w.getAppName());
                workerStartRequest.setWorkerKey(w.getWorkerKey());
                workerStartRequest.setProtocolType(w.getProtocolType());

                log.info("Scheduling worker start begin! address={}", w.getAddress());
                this.workerStart(workerStartRequest);
            }
        });

        // New fail worker.
        long offlinePos = DateUtil.timestamp() - this.clusterProperties.getWorker().getOfflinePeriod();
        List<Worker> onlineWorkers = Optional.ofNullable(workerMap.get(WorkerStatusEnum.ONLINE.getStatus())).orElseGet(ArrayList::new);
        onlineWorkers.forEach(w -> {
            // Fail worker
            if (w.getLastHeartbeatTime() < offlinePos) {
                WorkerStopRequest workerStopRequest = new WorkerStopRequest();
                workerStopRequest.setWorkerKey(w.getWorkerKey());
                workerStopRequest.setAddress(w.getAddress());
                workerStopRequest.setAppName(w.getAppName());

                log.info("Scheduling worker stop begin! address={}", w.getAddress());
                this.workerStop(workerStopRequest);
            }
        });
    }
}
