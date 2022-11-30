package io.openjob.server.scheduler.contract;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface DelayScheduler {

    /**
     * Delay scheduler start.
     */
    void start();

    /**
     * Delay scheduler stop.
     */
    void stop();

    /**
     * Refresh
     */
    void refresh();
}
