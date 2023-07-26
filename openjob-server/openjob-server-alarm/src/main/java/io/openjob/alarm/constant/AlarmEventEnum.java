package io.openjob.alarm.constant;

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
    JOB_EXECUTE_FAIL("job_001", "Job execute failed"),
    JOB_DISCARD("job_002", "Job discard"),
    JOB_EXECUTE_TIMEOUT("job_003", "Job execute timeout"),
    JOB_REACH_RETRY_TIMES("job_004", "Job reach retry times"),
    DELAY_EXECUTE_FAIL("delay_001", "Delay execute failed"),
    DELAY_EXECUTE_TIMEOUT("delay_002", "Delay execute timeout"),
    DELAY_TASK_IGNORE("delay_003", "Delay task ignore"),
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
