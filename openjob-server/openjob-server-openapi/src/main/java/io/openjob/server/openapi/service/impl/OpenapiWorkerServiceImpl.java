package io.openjob.server.openapi.service.impl;

import io.openjob.server.cluster.dto.WorkerStartReqDTO;
import io.openjob.server.cluster.dto.WorkerStartRespDTO;
import io.openjob.server.cluster.dto.WorkerStopReqDTO;
import io.openjob.server.cluster.dto.WorkerStopRespDTO;
import io.openjob.server.cluster.manager.WorkerManager;
import io.openjob.server.common.util.BeanMapperUtil;
import io.openjob.server.openapi.request.WorkerHeartbeatRequest;
import io.openjob.server.openapi.request.WorkerStartRequest;
import io.openjob.server.openapi.request.WorkerStopRequest;
import io.openjob.server.openapi.service.OpenapiWorkerService;
import io.openjob.server.openapi.vo.ServerHeartbeatVO;
import io.openjob.server.openapi.vo.ServerWorkerStartVO;
import io.openjob.server.openapi.vo.ServerWorkerStopVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
@Service
@Log4j2
public class OpenapiWorkerServiceImpl implements OpenapiWorkerService {
    private final WorkerManager workerManager;

    @Autowired
    public OpenapiWorkerServiceImpl(WorkerManager workerManager) {
        this.workerManager = workerManager;
    }

    @Override
    public ServerWorkerStartVO workerStart(WorkerStartRequest startRequest) {
        WorkerStartReqDTO reqDTO = BeanMapperUtil.map(startRequest, WorkerStartReqDTO.class);
        WorkerStartRespDTO workerStartRespDTO = this.workerManager.workerStart(reqDTO);
        return BeanMapperUtil.map(workerStartRespDTO, ServerWorkerStartVO.class);
    }

    @Override
    public ServerWorkerStopVO workerStop(WorkerStopRequest stopReq) {
        WorkerStopReqDTO reqDTO = BeanMapperUtil.map(stopReq, WorkerStopReqDTO.class);
        WorkerStopRespDTO workerStopRespDTO = this.workerManager.workerStop(reqDTO);
        return BeanMapperUtil.map(workerStopRespDTO, ServerWorkerStopVO.class);
    }

    @Override
    public ServerHeartbeatVO workerHeartbeat(WorkerHeartbeatRequest workerHeartbeatRequest) {
        return null;
    }
}
