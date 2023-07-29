package io.openjob.server.alarm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Getter
@AllArgsConstructor
public enum AlarmEventEnum {

    /**
     * Job execute fail
     */
    JOB_EXECUTE_FAIL("alarm.job.001", "Job execute failed"),

    /**
     * Job discard
     */
    JOB_DISCARD("alarm.job.002", "Job discard"),

    /**
     * Job execute timeout
     */
    JOB_EXECUTE_TIMEOUT("alarm.job.003", "Job execute timeout"),

    /**
     * Job reach retry times
     */
    JOB_REACH_RETRY_TIMES("alarm.job.004", "Job reach retry times"),

    /**
     *
     */
    DELAY_EXECUTE_FAIL("alarm.delay.001", "Delay execute failed"),

    /**
     * Delay execute timeout
     */
    DELAY_EXECUTE_TIMEOUT("alarm.delay.002", "Delay execute timeout"),

    /**
     * Delay task ignore
     */
    DELAY_TASK_IGNORE("alarm.delay.003", "Delay task ignore"),
    ;

    private final String event;
    private final String message;

    public static final List<String> JOB_EVENTS = Arrays.asList(
            JOB_EXECUTE_FAIL.event,
            JOB_DISCARD.event,
            JOB_EXECUTE_TIMEOUT.event,
            JOB_REACH_RETRY_TIMES.event
    );

    public static final List<String> DELAY_EVENTS = Arrays.asList(
            DELAY_EXECUTE_FAIL.event,
            DELAY_EXECUTE_TIMEOUT.event,
            DELAY_TASK_IGNORE.event
    );

    public static Boolean isJobEvent(String event) {
        return JOB_EVENTS.contains(event);
    }

    public static Boolean isDelayEvent(String event) {
        return DELAY_EVENTS.contains(event);
    }
}
