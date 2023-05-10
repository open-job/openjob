package io.openjob.server.common.exception;

import io.openjob.server.common.constant.BaseEnum;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface BaseExceptionAssert extends BaseEnum {

    /**
     * New exception.
     *
     * @param args args
     * @return RuntimeException
     */
    default RuntimeException newException(Object... args) {
        return new RuntimeException("Default exception");
    }

    /**
     * New exception.
     *
     * @param t    exception
     * @param args args
     * @return RuntimeException
     */
    default RuntimeException newException(Throwable t, Object... args) {
        return new RuntimeException("Default exception");
    }

    /**
     * Throw exception.
     */
    default void throwException() {
        throw newException();
    }

    /**
     * Assert is true.
     *
     * @param condition condition
     */
    default void assertIsTrue(Boolean condition) {
        if (!condition) {
            throw newException();
        }
    }

    /**
     * Assert is true.
     *
     * @param condition condition
     * @param args      args
     */
    default void assertIsTrue(Boolean condition, Object... args) {
        if (!condition) {
            throw newException(args);
        }
    }

    /**
     * Assert is true.
     *
     * @param condition condition
     * @param t         t
     * @param args      args
     */
    default void assertIsTrue(Boolean condition, Throwable t, Object... args) {
        if (!condition) {
            throw newException(t, args);
        }
    }

    /**
     * Assert not null.
     *
     * @param obj obj
     */
    default void assertNotNull(Object obj) {
        if (Objects.isNull(obj)) {
            throw newException();
        }
    }

    /**
     * Assert not null.
     *
     * @param obj  obj
     * @param args args
     */
    default void assertNotNull(Object obj, Object... args) {
        if (Objects.isNull(obj)) {
            throw newException(args);
        }
    }

    /**
     * Assert not null.
     *
     * @param obj  obj
     * @param t    t
     * @param args args
     */
    default void assertNotNull(Object obj, Throwable t, Object... args) {
        if (Objects.isNull(obj)) {
            throw newException(t, args);
        }
    }

    /**
     * Assert not empty.
     *
     * @param collection collection
     */
    default void assertNotEmpty(Collection<?> collection) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw newException();
        }
    }

    /**
     * Assert not empty.
     *
     * @param collection collection
     * @param args       args
     */
    default void assertNotEmpty(Collection<?> collection, Object... args) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * Assert not empty.
     *
     * @param collection collection
     * @param t          t
     * @param args       args
     */
    default void assertNotEmpty(Collection<?> collection, Throwable t, Object... args) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw newException(t, args);
        }
    }

    /**
     * Assert not empty.
     *
     * @param map map
     */
    default void assertNotEmpty(Map<?, ?> map) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw newException();
        }
    }

    /**
     * Assert not empty.
     *
     * @param map  map
     * @param args args
     */
    default void assertNotEmpty(Map<?, ?> map, Object... args) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * Assert not empty.
     *
     * @param map  map
     * @param t    t
     * @param args args
     */
    default void assertNotEmpty(Map<?, ?> map, Throwable t, Object... args) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw newException(t, args);
        }
    }

    /**
     * Assert not empty.
     *
     * @param array array
     */
    default void assertNotEmpty(Object[] array) {
        if (Objects.isNull(array) || ObjectUtils.isEmpty(array)) {
            throw newException();
        }
    }

    /**
     * Assert not empty.
     *
     * @param array array
     * @param args  args
     */
    default void assertNotEmpty(Object[] array, Object... args) {
        if (Objects.isNull(array) || ObjectUtils.isEmpty(array)) {
            throw newException(args);
        }
    }

    /**
     * Assert not empty.
     *
     * @param array array
     * @param t     t
     * @param args  args
     */
    default void assertNotEmpty(Object[] array, Throwable t, Object... args) {
        if (Objects.isNull(array) || ObjectUtils.isEmpty(array)) {
            throw newException(t, args);
        }
    }
}
