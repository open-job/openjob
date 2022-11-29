package io.openjob.server.scheduler.util;

import io.openjob.common.SpringContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class RedisUtil {
    @SuppressWarnings("unchecked")
    public static RedisTemplate<String, Object> getTemplate() {
        return SpringContext.getBean("redisTemplate", RedisTemplate.class);
    }

    /**
     * Pop and remove from list.
     *
     * @param key   key
     * @param count count
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<Object> popAndRemoveFromList(String key, Integer count) {
        final List<Object> txResults = RedisUtil.getTemplate().executePipelined(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(@Nonnull RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForList().range(key, 0, count - 1);
                operations.opsForList().trim(key, count, -1);
                return operations.exec();
            }
        });

        if (CollectionUtils.isEmpty(txResults)) {
            return Collections.emptyList();
        }
        Object result = txResults.get(0);
        if (result instanceof ArrayList) {
            return (List<Object>) result;
        }
        return Collections.emptyList();
    }

    /**
     * Or else get.
     *
     * @param key      key
     * @param supplier supplier
     * @param duration duration
     * @param <R>      r
     * @return R
     */
    @SuppressWarnings("unchecked")
    public static <R> R orElseGet(String key, Supplier<R> supplier, Duration duration) {
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
