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
