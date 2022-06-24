package io.openjob.server.scheduler.timer;

import io.openjob.server.scheduler.contract.Timer;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SystemTimer implements Timer {
    @Override
    public void add(TimerTask timerTask) {

    }

    @Override
    public Boolean advanceClock(Long timeoutSecond) {
        return null;
    }

    @Override
    public Integer size() {
        return null;
    }

    @Override
    public void shuntDown() {

    }
}
