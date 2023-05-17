package io.openjob.server.admin.constant;

import io.openjob.server.common.exception.CodeExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * user: 100+
 * namespace: 200+
 * application: 300+
 * job: 400+
 * delay: 500+
 *
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum CodeEnum implements CodeExceptionAssert {

    /**
     * App name not exist
     */
    NAME_EXIST(100, "App name must be globally unique!"),

    TIME_EXPRESSION_INVALID(400, "Time expression is invalid");

    /**
     * Value
     */
    private final Integer value;

    /**
     * Message
     */
    private final String message;
}
