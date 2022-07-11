package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum InstanceStatusEnum {

    /**
     * Waiting for dispatch.
     */
    waiting(1, "waiting"),

    /**
     * Has dispatch to worker.
     */
    dispatched(3, "dispatched"),

    /**
     * Worker received.
     */
    received(5, "received"),

    /**
     * Running.
     */
    RUNNING(7, "running"),

    /**
     * Success.
     */
    SUCCESS(9, "success"),

    /**
     * Fail.
     */
    FAIL(11, "fail"),

    /**
     * Stop.
     */
    STOP(13, "stop"),

    /**
     * Cancel.
     */
    CANCEL(15, "cancel"),
    ;

    private final Integer status;
    private final String message;
}
