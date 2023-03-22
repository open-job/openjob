package io.openjob.worker.container;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskProcessor {
    /**
     * Start
     */
    void start();

    /**
     * Stop
     */
    void stop();
}
