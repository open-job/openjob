package io.openjob.common.constant;

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
    WAITING(1, "waiting"),

    /**
     * Has dispatch to worker.
     */
    DISPATCHED(3, "dispatched"),

    /**
     * Worker received.
     */
    RECEIVED(5, "received"),

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
