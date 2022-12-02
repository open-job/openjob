package io.openjob.server.scheduler.util;

import io.openjob.common.SpringContext;
import io.openjob.server.scheduler.contract.KeyGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
     * Multi to get value
     *
     * @param prefix   cache prefix
     * @param ids      cache ids
     * @param function query data
     * @param duration expire time.
     * @param <T>      id type
     * @param <R>      value type
     * @return List.
     */
    public static <T, R> List<R> multiOrElseGet(String prefix, List<T> ids, Function<List<T>, Map<T, R>> function, Duration duration) {
        Pair<List<String>, List<T>> pairKeys = getKeyGenerator(KeyGenerator.defGenerator(prefix), ids);
        return doOrElseGet(pairKeys, function, duration);
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

    /**
     * Do multi get.
     *
     * @param keyPairs key generator.
     * @param function function.
     * @param duration duration.
     * @param <T>      id type.
     * @param <R>      value type.
     * @return List
     */
    @SuppressWarnings("unchecked")
    private static <T, R> List<R> doOrElseGet(Pair<List<String>, List<T>> keyPairs, Function<List<T>, Map<T, R>> function, Duration duration) {
        List<String> first = keyPairs.getFirst();
        List<T> second = keyPairs.getSecond();
        @SuppressWarnings("rawtypes")
        RedisTemplate redisTemplate = SpringContext.getBean("redisTemplate", RedisTemplate.class);

        // Multi get from cache.
        ValueOperations<String, R> operation = redisTemplate.opsForValue();
        List<R> value = operation.multiGet(first);

        // All in cache.
        assert value != null;
        int valueSize = value.size();
        if (isEntireKeysExist(value)) {
            return value;
        }

        MultiValueMap<String, Integer> keyForIndex = new LinkedMultiValueMap<>();
        Map<T, String> idForKey = new HashMap<>();
        List<T> nullKeysValues = new ArrayList<>();
        for (int i = 0; i < valueSize; i++) {
            // Not in cache.
            if (value.get(i) == null) {
                nullKeysValues.add(second.get(i));
            }

            keyForIndex.add(first.get(i), i);
            idForKey.put(second.get(i), first.get(i));
        }

        // Query from store.
        Map<T, R> result = function.apply(nullKeysValues);
        if (result != null && !result.isEmpty()) {

            // Build multiSet key map.
            Map<String, R> store = buildStoreMap(result, idForKey);

            // Multi set.
            operation.multiSet(store);

            // Batch set expire.
            redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
                store.forEach((k, v) -> {
                    if (duration != Duration.ZERO) {
                        byte[] rawKey = redisTemplate.getKeySerializer().serialize(k);
                        long rawTimeout = TimeoutUtils.toMillis(duration.toMillis(), TimeUnit.MILLISECONDS);
                        assert rawKey != null;
                        connection.pExpire(rawKey, rawTimeout);
                    }

                    Objects.requireNonNull(keyForIndex.get(k)).forEach(index -> value.set(index, v));
                });
                return null;
            });
        }
        return removeNullValue(value);
    }

    /**
     * Key generator.
     *
     * @param keyGenerator keyGenerator
     * @param ids          ids.
     * @param <T>          id type
     * @return List
     */
    private static <T> Pair<List<String>, List<T>> getKeyGenerator(KeyGenerator<T> keyGenerator, List<T> ids) {
        List<String> keys = ids.stream()
                .map(keyGenerator::generator)
                .collect(Collectors.toList());
        return Pair.of(keys, ids);
    }

    /**
     * Whether key is exist
     *
     * @param value list
     * @param <R>   value type
     * @return List
     */
    private static <R> Boolean isEntireKeysExist(List<R> value) {
        int count = 0, valueSize = value.size();
        for (R r : value) {
            if (r != null) {
                count++;
            }
        }
        return count == valueSize;
    }

    /**
     * Build map for multiSet
     *
     * @param result   result
     * @param idForKey id=>key map
     * @param <T>      id type
     * @param <R>      value type
     * @return Map
     */
    private static <T, R> Map<String, R> buildStoreMap(Map<T, R> result, Map<T, String> idForKey) {
        Map<String, R> store = new HashMap<>();
        for (Map.Entry<T, R> entry : result.entrySet()) {
            String key = idForKey.get(entry.getKey());
            if (key == null) {
                throw new IllegalAccessError(entry.getKey() + " is not exist!");
            }
            store.put(key, entry.getValue());
        }
        return store;
    }

    /**
     * Remove null item.
     *
     * @param value value
     * @param <R>   type
     * @return List
     */
    private static <R> List<R> removeNullValue(List<R> value) {
        value.removeIf(Objects::isNull);
        return value;
    }
}
