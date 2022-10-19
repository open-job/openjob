package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum StatusEnum {
    /**
     * Success.
     */
    SUCCESS(200, "ok"),

    /**
     * Fail.
     */
    FAIL(500, "fail"),

    /**
     * Not found
     */
    NOT_FOUND(404, "Not found"),

    /**
     * Invalid Argument
     */
    INVALID_ARGUMENT(406, "Invalid Argument"),

    ;

    /**
     * Status
     */
    private final Integer status;

    /**
     * Message
     */
    private final String message;
}
