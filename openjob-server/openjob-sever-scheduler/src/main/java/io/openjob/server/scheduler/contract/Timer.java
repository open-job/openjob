package io.openjob.server.scheduler.contract;

import io.openjob.server.scheduler.timer.TimerTask;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface Timer {
    void add(TimerTask timerTask);

    /**
     * @param timeout second.
     * @return Boolean
     */
    Boolean advanceClock(Long timeout);

    Integer size();

    void shuntDown();
}
