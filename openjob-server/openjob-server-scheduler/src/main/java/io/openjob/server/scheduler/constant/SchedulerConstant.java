package io.openjob.server.scheduler.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SchedulerConstant {

    /**
     * Initial delay.
     * Must use basic data type.
     */
    public static final long JOB_INITIAL_DELAY = 3000L;

    /**
     * Fixed_delay.
     * Must use basic data type.
     */
    public static final long JOB_FIXED_DELAY = 60000L;

    /**
     * Unit ms
     */
    public static final Integer UNIT_MS = 1000;

    /**
     * Delay retry time(s)
     */
    public static final Long DELAY_RETRY_AFTER = 10L;
}
