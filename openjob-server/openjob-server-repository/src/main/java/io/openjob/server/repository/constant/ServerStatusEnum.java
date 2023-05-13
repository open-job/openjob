package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum ServerStatusEnum {
    /**
     * Success.
     */
    OK(1, "ok"),

    /**
     * Failed.
     */
    FAIL(2, "fail"),
    ;

    private final Integer status;
    private final String message;
}
