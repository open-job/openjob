package io.openjob.server.repository.util;

import io.openjob.common.SpringContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class RedisUtil {
    @SuppressWarnings("unchecked")
    public static RedisTemplate<String, Object> getTemplate() {
        return SpringContext.getBean(RedisTemplate.class);
    }

    /**
     * Or else get.
     *
     * @param key      key
     * @param supplier supplier
     * @param duration duration
     * @param <R> r
     * @return R
     */
    @SuppressWarnings("unchecked")
    public static  <R> R orElseGet(String key, Supplier<R> supplier, Duration duration) {
        Optional.ofNullable(key).orElseThrow(() -> new IllegalArgumentException("Key is empty."));

        RedisTemplate<String, Object> redisTemplate = RedisUtil.getTemplate();
        ValueOperations<String, R> operation = (ValueOperations<String, R>) redisTemplate.opsForValue();
        R value = operation.get(key);
        if (value != null) {
            return value;
        }

        R r = supplier.get();
        if (r != null) {
            if (duration != Duration.ZERO) {
                operation.set(key, r, duration);
            } else {
                operation.set(key, r);
            }
        }
        return r;
    }
}
