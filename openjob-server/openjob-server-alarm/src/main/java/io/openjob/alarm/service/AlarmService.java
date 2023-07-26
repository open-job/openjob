package io.openjob.alarm.service;

import io.openjob.alarm.channel.AlarmChannel;
import io.openjob.alarm.constant.AlarmEventEnum;
import io.openjob.alarm.context.AlarmContext;
import io.openjob.alarm.dto.AlarmDTO;
import io.openjob.alarm.dto.AlarmEventDTO;
import io.openjob.server.repository.dao.AlertRuleDAO;
import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.entity.AlertRule;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
@Service
public class AlarmService {
    private final AlertRuleDAO alertRuleDAO;
    private final DelayDAO delayDAO;
    private final JobDAO jobDAO;
    private final Map<String, AlarmChannel> channelMap = new HashMap<>();

    @Autowired
    public AlarmService(List<AlarmChannel> channels, AlertRuleDAO alertRuleDAO, DelayDAO delayDAO, JobDAO jobDAO) {
        this.alertRuleDAO = alertRuleDAO;
        this.delayDAO = delayDAO;
        this.jobDAO = jobDAO;
        channels.forEach(c -> channelMap.put(c.channel().getType(), c));
    }

    /**
     * Alarm
     *
     * @param tasks tasks
     */
    public void alarm(List<AlarmEventDTO> tasks) {
        List<AlertRule> alarmRules = AlarmContext.getAlarmRules(this.alertRuleDAO::getEnableList);

        tasks.forEach(t -> {
            try {
                if (AlarmEventEnum.isJobEvent(t.getName())) {
                    this.jobAlarm(alarmRules, t);
                    return;
                }
                if (AlarmEventEnum.isDelayEvent(t.getName())) {
                    this.delayAlarm(alarmRules, t);
                    return;
                }

                log.error("Alarm event not supported! event={}", t.getName());
            } catch (Throwable throwable) {
                log.error("Alarm failed!", throwable);
            }
        });
    }

    private void jobAlarm(List<AlertRule> rules, AlarmEventDTO eventDTO) {
        Job job = AlarmContext.getJobById(Long.valueOf(eventDTO.getJobUniqueId()), this.jobDAO::getById);
        rules.forEach(r -> {
            // Not match
            if (!r.getNamespaceAppIdsByJson().contains(job.getAppId())) {
                return;
            }

            try {
                AlarmDTO alarmDTO = new AlarmDTO();
                alarmDTO.setAlertRule(r);
                alarmDTO.setEvent(eventDTO);
                alarmDTO.setJob(job);
                AlarmChannel channel = this.getChannel(r.getMethod());
                channel.send(alarmDTO);
            } catch (Throwable throwable) {
                log.error("Job alarm failed!", throwable);
            }
        });
    }

    private void delayAlarm(List<AlertRule> rules, AlarmEventDTO eventDTO) {
        Delay delay = AlarmContext.getDelayByTopic(eventDTO.getJobUniqueId(), this.delayDAO::findByTopic);
        rules.forEach(r -> {
            // Not match
            if (!r.getNamespaceAppIdsByJson().contains(delay.getAppId())) {
                return;
            }

            try {
                AlarmDTO alarmDTO = new AlarmDTO();
                alarmDTO.setAlertRule(r);
                alarmDTO.setEvent(eventDTO);
                alarmDTO.setDelay(delay);
                AlarmChannel channel = this.getChannel(r.getMethod());
                channel.send(alarmDTO);
            } catch (Throwable throwable) {
                log.error("Delay alarm failed!", throwable);
            }
        });
    }

    private AlarmChannel getChannel(String alertMethod) {
        return Optional.ofNullable(this.channelMap.get(alertMethod))
                .orElseThrow(() -> new RuntimeException("Alarm method not supported! method=" + alertMethod));
    }
}
