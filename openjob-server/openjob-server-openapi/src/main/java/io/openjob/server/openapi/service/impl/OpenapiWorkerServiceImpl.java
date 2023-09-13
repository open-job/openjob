package io.openjob.server.openapi.service.impl;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.common.dto.SystemDTO;
import io.openjob.server.common.util.SlotsUtil;
import io.openjob.server.openapi.autoconfigure.ClusterProperties;
import io.openjob.server.openapi.dto.WorkerFailDTO;
import io.openjob.server.openapi.dto.WorkerJoinDTO;
import io.openjob.server.openapi.executor.OpenapiWorkerHeartbeatExecutor;
import io.openjob.server.openapi.manager.OpenapiRefreshManager;
import io.openjob.server.openapi.request.WorkerHeartbeatRequest;
import io.openjob.server.openapi.request.WorkerStartRequest;
import io.openjob.server.openapi.request.WorkerStopRequest;
import io.openjob.server.openapi.service.OpenapiWorkerService;
import io.openjob.server.openapi.util.ClusterUtil;
import io.openjob.server.openapi.vo.ServerHeartbeatVO;
import io.openjob.server.openapi.vo.ServerWorkerStartVO;
import io.openjob.server.openapi.vo.ServerWorkerStopVO;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.AppDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.App;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Service
@Log4j2
public class OpenapiWorkerServiceImpl implements OpenapiWorkerService {

    private final WorkerDAO workerDAO;
    private final AppDAO appDAO;
    private final ClusterProperties clusterProperties;
    private final OpenapiRefreshManager refreshManager;
    private final OpenapiWorkerHeartbeatExecutor workerHeartbeatExecutor;
    private final JobInstanceDAO jobInstanceDAO;

    @Autowired
    public OpenapiWorkerServiceImpl(WorkerDAO workerDAO,
                                    AppDAO appDAO,
                                    ClusterProperties clusterProperties,
                                    OpenapiRefreshManager refreshManager,
                                    OpenapiWorkerHeartbeatExecutor workerHeartbeatExecutor,
                                    JobInstanceDAO jobInstanceDAO) {
        this.workerDAO = workerDAO;
        this.appDAO = appDAO;
        this.clusterProperties = clusterProperties;
        this.refreshManager = refreshManager;
        this.workerHeartbeatExecutor = workerHeartbeatExecutor;
        this.jobInstanceDAO = jobInstanceDAO;
    }

    @Override
    public ServerWorkerStartVO workerStart(WorkerStartRequest startRequest) {
        // Check app name.
        App app = this.checkAppName(startRequest.getAppName());

        // Do worker start.
        OpenjobSpringContext.getBean(this.getClass()).doWorkerStart(startRequest, app);

        // Akka message for worker start.
        WorkerJoinDTO workerJoinDTO = new WorkerJoinDTO();
        workerJoinDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
        ClusterUtil.sendMessage(workerJoinDTO, ClusterContext.getCurrentNode(), this.clusterProperties.getSpreadSize(), new HashSet<>());

        // Online workers  and exclude start worker.
        Set<String> onlineWorkers = ClusterUtil.getOnlineWorkers(app.getId());

        // VO
        ServerWorkerStartVO response = new ServerWorkerStartVO();
        response.setAppId(app.getId());
        response.setAppName(app.getName());
        response.setWorkerAddressList(onlineWorkers);
        return response;
    }

    /**
     * Do worker start.
     *
     * @param workerStartRequest workerStartRequest
     */
    @Transactional(rollbackFor = Exception.class, timeout = 1)
    public void doWorkerStart(WorkerStartRequest workerStartRequest, App app) {
        // Refresh system.
        // Lock system cluster version.
        this.refreshManager.refreshSystem(true);

        // Update worker status.
        this.updateWorkerForStart(workerStartRequest, app);

        // Refresh cluster context.
        refreshClusterContext();
    }

    @Override
    public ServerWorkerStopVO workerStop(WorkerStopRequest stopReq) {
        // Check app name.
        this.checkAppName(stopReq.getAppName());

        // Do worker stop.
        OpenjobSpringContext.getBean(this.getClass()).doWorkerStop(stopReq);

        // Akka message for worker start.
        WorkerFailDTO workerFailDTO = new WorkerFailDTO();
        workerFailDTO.setClusterVersion(ClusterContext.getSystem().getClusterVersion());
        ClusterUtil.sendMessage(workerFailDTO, ClusterContext.getCurrentNode(), this.clusterProperties.getSpreadSize(), new HashSet<>());

        return new ServerWorkerStopVO();
    }

    @Override
    public ServerHeartbeatVO workerHeartbeat(WorkerHeartbeatRequest workerHeartbeatRequest) {
        //Submit request
        this.workerHeartbeatExecutor.submit(workerHeartbeatRequest);

        ServerHeartbeatVO response = new ServerHeartbeatVO();
        ServerHeartbeatVO.HeartbeatSystemVO systemResponse = new ServerHeartbeatVO.HeartbeatSystemVO();

        // System information.
        SystemDTO system = ClusterContext.getSystem();
        systemResponse.setClusterVersion(system.getClusterVersion());
        systemResponse.setClusterDelayVersion(system.getClusterDelayVersion());
        response.setSystemResponse(systemResponse);

        // Online workers  and exclude start worker.
        Set<String> onlineWorkers = ClusterUtil.getOnlineWorkers(workerHeartbeatRequest.getAppId());
        response.setWorkerAddressList(onlineWorkers);
        return response;
    }

    /**
     * Do worker stop.
     *
     * @param stopReq stopReq
     */
    @Transactional(rollbackFor = Exception.class)
    public void doWorkerStop(WorkerStopRequest stopReq) {
        // Refresh system.
        // Lock system cluster version.
        this.refreshManager.refreshSystem(true);

        Worker worker = workerDAO.getByAddress(stopReq.getAddress());
        if (Objects.isNull(worker)) {
            log.error("worker({}) do not exist!", stopReq.getAddress());
            return;
        }

        // Update worker
        worker.setStatus(WorkerStatusEnum.OFFLINE.getStatus());
        worker.setUpdateTime(DateUtil.timestamp());
        workerDAO.save(worker);

        // Refresh cluster context.
        this.refreshClusterContext();
    }

    /**
     * Batch heartbeat
     *
     * @param requests requests
     */
    @Override
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

    private void refreshClusterContext() {
        List<Worker> workers = workerDAO.listOnlineWorkers();
        ClusterUtil.refreshAppWorkers(workers);

        log.info("Refresh app workers={}", workers.stream().map(Worker::getAddress).collect(Collectors.toList()));
    }

    /**
     * Check app name.
     *
     * @param appName app name.
     * @return App
     */
    private App checkAppName(String appName) {
        App app = appDAO.getAppByName(appName);
        if (Objects.isNull(app)) {
            throw new RuntimeException(String.format("Register application(%s) do not exist!", appName));
        }
        return app;
    }

    private void updateWorkerForStart(WorkerStartRequest startReq, App app) {
        // Update worker.
        long now = DateUtil.timestamp();
        Worker worker = workerDAO.getByAddress(startReq.getAddress());
        if (Objects.nonNull(worker)) {
            worker.setWorkerKey(worker.getWorkerKey());
            worker.setStatus(WorkerStatusEnum.ONLINE.getStatus());

            //Must update last heartbeat time.
            worker.setLastHeartbeatTime(now);
            worker.setUpdateTime(now);

            // Update latest app name
            worker.setAppName(startReq.getAppName());
            worker.setNamespaceId(app.getNamespaceId());
            worker.setAppId(app.getId());
            worker.setProtocolType(startReq.getProtocolType());
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
