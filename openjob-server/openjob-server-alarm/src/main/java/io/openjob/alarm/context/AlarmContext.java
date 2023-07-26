package io.openjob.alarm.context;

import com.google.common.collect.Maps;
import io.openjob.server.repository.entity.AlertRule;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.entity.Job;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Slf4j
public class AlarmContext {

    /**
     * Alert rules
     */
    private static final List<AlertRule> ALARM_RULES = new ArrayList<>();

    /**
     * jobId => Job
     */
    private static final Map<Long, Job> JOB_MAP = Maps.newConcurrentMap();

    /**
     * topic => Delay
     */
    private static final Map<String, Delay> DELAY_MAP = Maps.newConcurrentMap();

    public static synchronized void refreshAlarmRules() {
        ALARM_RULES.clear();
        log.info("Refresh alarm rules success!");
    }

    public static synchronized void refreshJobMap() {
        JOB_MAP.clear();
        log.info("Refresh alarm job map success!");
    }

    public static synchronized void refreshDelayMap() {
        DELAY_MAP.clear();
        log.info("Refresh alarm delay map success!");
    }

    public static List<AlertRule> getAlarmRules() {
        return ALARM_RULES;
    }

    public static synchronized Job getJobById(Long jobId, Function<Long, Job> function) {
        return JOB_MAP.computeIfAbsent(jobId, function);
    }

    public static synchronized Delay getDelayByTopic(String topic, Function<String, Delay> function) {
        return DELAY_MAP.computeIfAbsent(topic, function);
    }
}
