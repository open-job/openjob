package io.openjob.worker.container;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskContainer {
    void start() throws ClassNotFoundException;

    void stop();
}
