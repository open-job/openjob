package io.openjob.worker.master;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface TaskMaster {
    void submit();

    void stop();
}
