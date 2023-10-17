package io.openjob.server.openapi.service;

import io.openjob.server.openapi.request.WorkerHeartbeatRequest;
import io.openjob.server.openapi.request.WorkerStartRequest;
import io.openjob.server.openapi.request.WorkerStopRequest;
import io.openjob.server.openapi.vo.ServerHeartbeatVO;
import io.openjob.server.openapi.vo.ServerWorkerStartVO;
import io.openjob.server.openapi.vo.ServerWorkerStopVO;

/**
 * @author zhenghongyang sakuraovq@gmail.com
 * @since 1.0.0
 */
public interface OpenapiWorkerService {

    /**
     * Worker start
     *
     * @param startRequest start request.
     * @return Register success response.
     */
    ServerWorkerStartVO workerStart(WorkerStartRequest startRequest);

    /**
     * Worker stop
     *
     * @param stopRequest stop request.
     * @return Stop success response.
     */
    ServerWorkerStopVO workerStop(WorkerStopRequest stopRequest);

    /**
     * Worker heartbeat
     *
     * @param workerHeartbeatRequest heartbeat request.
     * @return Current server all worker address
     */
    ServerHeartbeatVO workerHeartbeat(WorkerHeartbeatRequest workerHeartbeatRequest);
}
