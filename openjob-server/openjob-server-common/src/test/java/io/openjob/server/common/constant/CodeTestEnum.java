package io.openjob.server.common.constant;

import io.openjob.server.common.exception.CodeExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum CodeTestEnum implements CodeExceptionAssert {
    ONE(1, "code message");

    private Integer value;
    private String message;
}
