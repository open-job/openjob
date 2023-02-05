package io.openjob.server.common.exception;

import io.openjob.server.common.constant.HttpStatusTestEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class HttpStatusExceptionAssertTest {
    @Test
    public void testThrownException() {
        Assertions.assertThrows(HttpStatusException.class, HttpStatusTestEnum.ONE::throwException);
    }

    @Test
    public void testAssert() {
        Assertions.assertThrows(HttpStatusException.class, () -> HttpStatusTestEnum.ONE.assertIsTrue(false));
    }
}
