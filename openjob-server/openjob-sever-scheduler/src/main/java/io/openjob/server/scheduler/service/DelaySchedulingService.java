package io.openjob.server.scheduler.service;

import com.google.common.collect.Lists;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.repository.data.DelayData;
import io.openjob.server.scheduler.timer.DelayTimerTask;
import io.openjob.server.scheduler.timer.TimerTask;
import io.openjob.server.scheduler.wheel.DelayWheel;
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

    private final DelayWheel delayWheel;

    private final DelayData delayData;

    public DelaySchedulingService(DelayInstanceDAO delayInstanceDAO, DelayWheel delayWheel, DelayData delayData) {
        this.delayInstanceDAO = delayInstanceDAO;
        this.delayWheel = delayWheel;
        this.delayData = delayData;
    }

    public void delayJob() {
        ArrayList<Long> currentSlots = new ArrayList<>(ClusterContext.getCurrentSlots());
        int maxExecuteTime = DateUtil.now() + (int) (SchedulerConstant.JOB_FIXED_DELAY / 1000L);

        while (true) {
            List<DelayInstance> delayInstances = this.delayInstanceDAO.listDelayInstance(currentSlots, maxExecuteTime, 200);
            if (Objects.isNull(delayInstances) || delayInstances.isEmpty()) {
                break;
            }

            List<Long> ids = Lists.newArrayList();
            List<TimerTask> tasks = new ArrayList<>();
            delayInstances.forEach(d -> {
                Delay delay = this.delayData.getDelay(d.getTopic());
                DelayTimerTask delayTimerTask = new DelayTimerTask(d.getId(), d.getSlotsId(), (long) d.getExecuteTime());
                delayTimerTask.setDelayId(d.getDelayId());
                delayTimerTask.setDelayParams(d.getDelayParams());
                delayTimerTask.setDelayExtra(d.getDelayExtra());
                delayTimerTask.setTopic(d.getTopic());
                delayTimerTask.setProcessorInfo(delayTimerTask.getProcessorInfo());
                delayTimerTask.setFailRetryInterval(delayTimerTask.getFailRetryInterval());
                delayTimerTask.setFailRetryTimes(delay.getFailRetryTimes());
                delayTimerTask.setExecuteTimeout(delay.getExecuteTimeout());
                delayTimerTask.setConcurrency(delay.getConcurrency());
            });

            // Add to timing wheel.
            this.delayWheel.addTimerTask(tasks);

            // Update delay instance status.
            this.delayInstanceDAO.batchUpdateStatus(ids, InstanceStatusEnum.DISPATCHED.getStatus());
        }
    }
}
