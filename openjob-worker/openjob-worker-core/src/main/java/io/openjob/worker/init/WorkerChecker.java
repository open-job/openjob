package io.openjob.worker.init;

import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
@Slf4j
public class WorkerChecker {

    /**
     * Init
     */
    public void init() {
        try {
            Thread.currentThread().getContextClassLoader().loadClass("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
