package io.openjob.server.cluster.service;

import io.openjob.common.request.WorkerHeartbeatRequest;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.util.LogUtil;
import io.openjob.server.repository.dao.WorkerDAO;
import io.openjob.server.repository.entity.Worker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public void workerHeartbeat(WorkerHeartbeatRequest workerHeartbeatReq) {
        Worker worker = workerDAO.getByAddress(workerHeartbeatReq.getAddress());
        if (Objects.isNull(worker)) {
            LogUtil.logAndThrow(String.format("worker(%s) do not exist!", workerHeartbeatReq.getAddress()));
        }

        int now = DateUtil.now();
        worker.setUpdateTime(now);
        worker.setLastHeartbeatTime(now);
        workerDAO.save(worker);
    }
}
