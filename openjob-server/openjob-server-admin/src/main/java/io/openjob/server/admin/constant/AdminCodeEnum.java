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
public enum AdminCodeEnum implements CodeExceptionAssert {

    /**
     * Test
     */
    TEST(1, "TEST");

    /**
     * Value
     */
    private final Integer value;

    /**
     * Message
     */
    private final String message;
}
