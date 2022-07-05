package io.openjob.server.scheduler.constant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class TimerConstant {
    /**
     * Tick time(s).
     */
    public static final Long TICK_TIME = 1L;

    /**
     * Wheel size.
     */
    public static final Integer WHEEL_SIZE = 20;

    /**
     * Timer clock time(ms).
     */
    public static final Long TIMER_CLOCK_TIME = 800L;

    /**
     * Timer thread name prefix.
     */
    public static final String TIMER_THREAD_NAME_PREFIX = "system-timer";
}
