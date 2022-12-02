package io.openjob.server.scheduler.contract;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@FunctionalInterface
public interface KeyGenerator<T> {

    String generator(T t);

    static <T> KeyGenerator<T> defGenerator(String keyPrefix) {
        return key -> keyPrefix + key.toString();
    }
}