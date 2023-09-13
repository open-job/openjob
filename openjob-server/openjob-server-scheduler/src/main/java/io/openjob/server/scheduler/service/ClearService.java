package io.openjob.server.scheduler.service;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.repository.constant.ServerStatusEnum;
import io.openjob.server.repository.constant.WorkerStatusEnum;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dao.ServerDAO;
import io.openjob.server.repository.dao.WorkerDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Slf4j
@Service
public class ClearService {
    private final JobInstanceDAO jobInstanceDAO;
    private final DelayInstanceDAO delayInstanceDAO;
    private final ServerDAO serverDAO;
    private final WorkerDAO workerDAO;
    private final LogDAO logDAO;


    /**
     * Init
     *
     * @param jobInstanceDAO   jobInstanceDAO
     * @param delayInstanceDAO delayInstanceDAO
     * @param serverDAO        serverDAO
     * @param workerDAO        workerDAO
     * @param logDAO           logDAO
     */
    public ClearService(JobInstanceDAO jobInstanceDAO,
                        DelayInstanceDAO delayInstanceDAO,
                        ServerDAO serverDAO,
                        WorkerDAO workerDAO,
                        LogDAO logDAO) {
        this.jobInstanceDAO = jobInstanceDAO;
        this.delayInstanceDAO = delayInstanceDAO;
        this.serverDAO = serverDAO;
        this.workerDAO = workerDAO;
        this.logDAO = logDAO;
    }

    /**
     * Clear data
     */
    public void clearData() {
        // First slots to clear data
        Set<Long> currentSlots = ClusterContext.getCurrentSlots();
        if (!currentSlots.contains(1L)) {
            return;
        }

        try {
            // Do clear
            this.doClear();
        } catch (Exception e) {
            log.error("System data clear failed!", e);
        }
    }

    /**
     * Do clear
     *
     * @throws Exception Exception
     */
    private void doClear() throws Exception {
        // Data keep days
        Long oneDay = 60 * 60 * 24L;
        long timestamp = DateUtil.timestamp();
        Integer jobKeepDays = ClusterContext.getSystem().getJobKeepDays();
        Integer delayKeepDays = ClusterContext.getSystem().getDelayKeepDays();
        Integer serverKeepDays = ClusterContext.getSystem().getServerKeepDays();
        Integer workerKeepDays = ClusterContext.getSystem().getWorkerKeepDays();
        Integer logKeepDays = jobKeepDays >= delayKeepDays ? jobKeepDays : delayKeepDays;

        // Job instance
        Long jobInstanceCount = this.jobInstanceDAO.deleteByCreateTime(timestamp - oneDay * jobKeepDays, InstanceStatusEnum.COMPLETE);

        // Delay instance
        Long delayInstanceCount = this.delayInstanceDAO.deleteByCreateTimeAndNotStatus(
                timestamp - oneDay * delayKeepDays, TaskStatusEnum.INIT.getStatus());

        // Server
        Long serverCount = this.serverDAO.deleteByCreateTimeAndStatus(
                timestamp - oneDay * serverKeepDays, ServerStatusEnum.FAIL.getStatus());

        //  Worker
        Long workerCount = this.workerDAO.deleteByCreateTimeAndStatus(
                timestamp - oneDay * workerKeepDays, WorkerStatusEnum.OFFLINE.getStatus());

        log.info("System data clear success!jobInstanceCount={} delayInstanceCount={} serverCount={} workerCount={}",
                jobInstanceCount, delayInstanceCount, serverCount, workerCount);

        // Processor log
        this.logDAO.deleteByLastTime((timestamp - oneDay * logKeepDays) * 1000);
    }
}
