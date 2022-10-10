package io.openjob.server.scheduler.service;

import io.openjob.common.util.DateUtil;
import io.openjob.server.common.ClusterContext;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.scheduler.constant.SchedulerConstant;
import io.openjob.server.scheduler.data.DelayData;
import io.openjob.server.scheduler.timer.DelayTimerTask;
import io.openjob.server.scheduler.timer.TimerTask;
import io.openjob.server.scheduler.wheel.DelayWheel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Service
public class DelayService {
    private final DelayInstanceDAO delayInstanceDAO;

    private final DelayWheel delayWheel;

    private final DelayData delayData;

    public DelayService(DelayInstanceDAO delayInstanceDAO, DelayWheel delayWheel, DelayData delayData) {
        this.delayInstanceDAO = delayInstanceDAO;
        this.delayWheel = delayWheel;
        this.delayData = delayData;
    }

    public void delayJob() {
        ArrayList<Long> currentSlots = new ArrayList<>(ClusterContext.getCurrentSlots());

        int maxExecuteTime = DateUtil.now() + (int) (SchedulerConstant.JOB_FIXED_DELAY / 1000L);
        List<DelayInstance> delayInstances = this.delayInstanceDAO.listDelayInstance(currentSlots, maxExecuteTime);

        List<TimerTask> tasks = new ArrayList<>();
        delayInstances.forEach(d -> {
            Delay delay = this.delayData.getDelay(d.getNamespaceId(), d.getTopic());
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

        this.delayWheel.addTimerTask(tasks);
    }
}
