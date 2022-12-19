package io.openjob.server.scheduler.contract;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@FunctionalInterface
public interface KeyGenerator<T> {

    /**
     * Key generator.
     *
     * @param t type
     * @return String
     */
    String generator(T t);

    /**
     * Default generator.
     *
     * @param keyPrefix key prefix.
     * @param <T>       type
     * @return KeyGenerator
     */
    static <T> KeyGenerator<T> defGenerator(String keyPrefix) {
        return key -> String.format("%s:%s", keyPrefix, key.toString());
    }
}