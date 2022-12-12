package io.openjob.server.cluster.service;

import com.google.common.collect.Lists;
import io.openjob.common.SpringContext;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.request.WorkerStartRequest;
import io.openjob.common.request.WorkerStopRequest;
import io.openjob.common.util.CommonUtil;
import io.openjob.common.util.DateUtil;
import io.openjob.server.cluster.autoconfigure.ClusterProperties;
import io.openjob.server.cluster.constant.ClusterConstant;
import io.openjob.server.cluster.dto.WorkerFailDTO;
import io.openjob.server.cluster.dto.WorkerJoinDTO;
import io.openjob.server.cluster.manager.RefreshManager;
import io.openjob.server.cluster.util.ClusterUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.util.SlotsUtil;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
@Log4j2
public class WorkerService {
    private final WorkerDAO workerDAO;
    private final AppDAO appDAO;
    private final ClusterProperties clusterProperties;
    private final RefreshManager refreshManager;

    @Autowired
    public WorkerService(WorkerDAO workerDAO, AppDAO appDAO, ClusterProperties clusterProperties, RefreshManager refreshManager) {
        this.workerDAO = workerDAO;
        this.appDAO = appDAO;
        this.clusterProperties = clusterProperties;
        this.refreshManager = refreshManager;
    }

    /**
     * Worker start
     *
     * @param workerStartRequest start request.
     */
    public void workerStart(WorkerStartRequest workerStartRequest) {
        // Do worker start.
        SpringContext.getBean(this.getClass()).doWorkerStart(workerStartRequest);

        // Akka message for worker start.
        WorkerJoinDTO workerJoinDTO = new WorkerJoinDTO();
        workerJoinDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
        ClusterUtil.sendMessage(workerJoinDTO, ClusterContext.getCurrentNode(), this.clusterProperties.getSpreadSize(), new HashSet<>());
    }

    public void doWorkerStart(WorkerStartRequest workerStartRequest) {
        // Update worker status.
        this.updateWorkerForStart(workerStartRequest);

        // Refresh system
        this.refreshManager.refreshSystem(true);

        // Refresh cluster context.
        refreshClusterContext();
    }

    /**
     * Worker stop
     *
     * @param stopReq stop request.
     */
    public void workerStop(WorkerStopRequest stopReq) {
        // Do worker stop.
        SpringContext.getBean(this.getClass()).doWorkerStop(stopReq);

        // Akka message for worker start.
        WorkerFailDTO workerFailDTO = new WorkerFailDTO();
        workerFailDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
        ClusterUtil.sendMessage(workerFailDTO, ClusterContext.getCurrentNode(), this.clusterProperties.getSpreadSize(), new HashSet<>());
    }

    @Transactional
    public void doWorkerStop(WorkerStopRequest stopReq) {
        Worker worker = workerDAO.getByAddress(stopReq.getAddress());
        if (Objects.isNull(worker)) {
            log.error("worker({}) do not exist!", stopReq.getAddress());
            return;
        }

        // Update worker
        worker.setStatus(WorkerStatusEnum.OFFLINE.getStatus());
        worker.setUpdateTime(DateUtil.timestamp());
        workerDAO.save(worker);

        // Refresh system
        this.refreshManager.refreshSystem(true);

        // Refresh cluster context.
        this.refreshClusterContext();
    }


    /**
     * Worker check
     */
    public void workerCheck() {
        // Query all workers.
        long timePos = DateUtil.timestamp() - ClusterConstant.WORKER_CHECK_DELAY;
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
        List<Worker> offlineWorkers = Optional.ofNullable(workerMap.get(WorkerStatusEnum.OFFLINE.getStatus())).orElseGet(ArrayList::new);
        offlineWorkers.forEach(w -> {
            // Join worker
            if (w.getLastHeartbeatTime() > timePos) {
                WorkerStartRequest workerStartRequest = new WorkerStartRequest();
                workerStartRequest.setAddress(w.getAddress());
                workerStartRequest.setAppName(w.getAppName());
                workerStartRequest.setWorkerKey(w.getWorkerKey());
                this.workerStart(workerStartRequest);
            }
        });

        // New fail worker.
        List<Worker> onlineWorkers = Optional.ofNullable(workerMap.get(WorkerStatusEnum.ONLINE.getStatus())).orElseGet(ArrayList::new);
        onlineWorkers.forEach(w -> {
            // Fail worker
            if (w.getLastHeartbeatTime() < timePos) {
                WorkerStopRequest workerStopRequest = new WorkerStopRequest();
                workerStopRequest.setWorkerKey(w.getWorkerKey());
                workerStopRequest.setAddress(w.getAddress());
                workerStopRequest.setAppName(w.getAppName());
                this.workerStop(workerStopRequest);
            }
        });
    }

    private void refreshClusterContext() {
        List<Worker> workers = workerDAO.listOnlineWorkers();
        ClusterUtil.refreshAppWorkers(workers);
    }

    private void updateWorkerForStart(WorkerStartRequest startReq) {
        // app
        App app = appDAO.getAppByName(startReq.getAppName());
        if (Objects.isNull(app)) {
            throw new RuntimeException(String.format("app(%s) do not exist!", startReq.getAppName()));
        }

        // Update worker.
        long now = DateUtil.timestamp();
        Worker worker = workerDAO.getByAddress(startReq.getAddress());
        if (Objects.nonNull(worker)) {
            worker.setWorkerKey(worker.getWorkerKey());
            worker.setStatus(WorkerStatusEnum.ONLINE.getStatus());
            workerDAO.save(worker);
            return;
        }

        // save
        Worker saveWorker = new Worker();
        saveWorker.setUpdateTime(now);
        saveWorker.setCreateTime(now);
        saveWorker.setWorkerKey(startReq.getWorkerKey());
        saveWorker.setAddress(startReq.getAddress());
        saveWorker.setSlotsId(SlotsUtil.getWorkerSupervisorSlotsId(UUID.randomUUID().toString()));
        saveWorker.setStatus(WorkerStatusEnum.ONLINE.getStatus());
        saveWorker.setAppId(app.getId());
        saveWorker.setAppName(startReq.getAppName());
        saveWorker.setLastHeartbeatTime(now);
        saveWorker.setNamespaceId(app.getNamespaceId());
        saveWorker.setProtocolType(startReq.getProtocolType());
        saveWorker.setVersion(startReq.getVersion());
        saveWorker.setMetric("");
        saveWorker.setVersion("");
        saveWorker.setWorkerKey("");
        saveWorker.setDeleteTime(0L);
        saveWorker.setDeleted(CommonConstant.NO);
        workerDAO.save(saveWorker);
    }
}
