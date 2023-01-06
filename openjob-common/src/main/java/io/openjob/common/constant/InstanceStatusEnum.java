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
     * Running.
     */
    RUNNING(5, "running"),

    /**
     * Success.
     */
    SUCCESS(10, "success"),

    /**
     * Fail.
     */
    FAIL(15, "fail"),

    /**
     * Stop.
     */
    STOP(20, "stop"),

    /**
     * Cancel.
     */
    CANCEL(25, "cancel"),
    ;

    private final Integer status;
    private final String message;
}
