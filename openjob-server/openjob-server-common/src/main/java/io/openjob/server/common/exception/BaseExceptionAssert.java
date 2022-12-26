package io.openjob.server.common.exception;

import io.openjob.server.common.constant.BaseEnum;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface BaseExceptionAssert extends BaseEnum {

    default RuntimeException newException(Object... args) {
        return new RuntimeException("Default exception");
    }

    default RuntimeException newException(Throwable t, Object... args) {
        return new RuntimeException("Default exception");
    }

    default void throwException() {
        throw newException();
    }

    default void assertIsTrue(Boolean condition) {
        if (!condition) {
            throw newException();
        }
    }

    default void assertIsTrue(Boolean condition, Object... args) {
        if (!condition) {
            throw newException(args);
        }
    }

    default void assertIsTrue(Boolean condition, Throwable t, Object... args) {
        if (!condition) {
            throw newException(t, args);
        }
    }

    default void assertNotNull(Object obj) {
        if (Objects.isNull(obj)) {
            throw newException();
        }
    }

    default void assertNotNullWithData(Object obj, Object data) {
        if (Objects.isNull(obj)) {
            throw newException(data, new Object[0]);
        }
    }

    default void assertNotNull(Object obj, Object... args) {
        if (Objects.isNull(obj)) {
            throw newException(args);
        }
    }

    default void assertNotNull(Object obj, Throwable t, Object... args) {
        if (Objects.isNull(obj)) {
            throw newException(t, args);
        }
    }

    default void assertNotEmpty(Collection<?> collection) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw newException();
        }
    }

    default void assertNotEmpty(Collection<?> collection, Object... args) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw newException(args);
        }
    }

    default void assertNotEmpty(Collection<?> collection, Throwable t, Object... args) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw newException(t, args);
        }
    }

    default void assertNotEmpty(Map<?, ?> map) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw newException();
        }
    }

    default void assertNotEmptyWithData(Map<?, ?> map, Object data) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw newException(data, new Object[0]);
        }
    }

    default void assertNotEmpty(Map<?, ?> map, Object... args) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw newException(args);
        }
    }

    default void assertNotEmpty(Map<?, ?> map, Throwable t, Object... args) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw newException(t, args);
        }
    }

    default void assertNotEmpty(Object[] array) {
        if (Objects.isNull(array) || ObjectUtils.isEmpty(array)) {
            throw newException();
        }
    }

    default void assertNotEmpty(Object[] array, Object... args) {
        if (Objects.isNull(array) || ObjectUtils.isEmpty(array)) {
            throw newException(args);
        }
    }

    default void assertNotEmpty(Object[] array, Throwable t, Object... args) {
        if (Objects.isNull(array) || ObjectUtils.isEmpty(array)) {
            throw newException(t, args);
        }
    }
}
