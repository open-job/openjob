package io.openjob.server.scheduler.service;

import com.google.common.collect.Lists;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelaySchedulingService {
    private final DelayInstanceDAO delayInstanceDAO;


    /**
     * New delay scheduling service.
     *
     * @param delayInstanceDAO delay instance.
     */
    public DelaySchedulingService(DelayInstanceDAO delayInstanceDAO) {
        this.delayInstanceDAO = delayInstanceDAO;
    }

    /**
     * Delay job.
     */
    public void delayJob() {
        ArrayList<Long> currentSlots = new ArrayList<>(ClusterContext.getCurrentSlots());
        int maxExecuteTime = DateUtil.now() + (int) (SchedulerConstant.JOB_FIXED_DELAY / 1000L);

        while (true) {
            List<DelayInstance> delayInstances = this.delayInstanceDAO.listDelayInstance(currentSlots, maxExecuteTime, 200);
            if (Objects.isNull(delayInstances) || delayInstances.isEmpty()) {
                break;
            }

            List<Long> ids = Lists.newArrayList();
            delayInstances.forEach(d -> {
            });

            // Update delay instance status.
            this.delayInstanceDAO.batchUpdateStatus(ids, InstanceStatusEnum.DISPATCHED.getStatus());
        }
    }
}
