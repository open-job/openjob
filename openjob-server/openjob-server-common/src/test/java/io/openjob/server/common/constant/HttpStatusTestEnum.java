package io.openjob.server.common.constant;

import io.openjob.server.common.exception.CodeExceptionAssert;
import io.openjob.server.common.exception.HttpStatusExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum HttpStatusTestEnum implements HttpStatusExceptionAssert {
    ONE(1, "http status message");

    private final Integer value;
    private final String message;
}
