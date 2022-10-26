package io.openjob.server.scheduler.contract;

import io.openjob.server.scheduler.timer.AbstractTimerTask;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface Timer {

    /**
     * Add tasker
     *
     * @param timerTask
     */
    void add(AbstractTimerTask timerTask);

    /**
     * Advance clock.
     *
     * @param timeout second.
     * @return Boolean
     */
    Boolean advanceClock(Long timeout);

    /**
     * Task size.
     *
     * @return Size.
     */
    Integer size();

    /**
     * Shutdown timer.
     */
    void shuntDown();
}
