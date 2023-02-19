package io.openjob.server.admin.constant;

import io.openjob.server.common.exception.CodeExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum AppCodeEnum implements CodeExceptionAssert {

    /**
     * App
     */
    NAME_EXIST(1, "App name must be globally unique!");

    /**
     * Value
     */
    private final Integer value;

    /**
     * Message
     */
    private final String message;
}
