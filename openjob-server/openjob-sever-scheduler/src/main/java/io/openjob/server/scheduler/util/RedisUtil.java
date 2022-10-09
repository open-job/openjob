package io.openjob.server.scheduler.util;

import io.openjob.common.SpringContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class RedisUtil {
    @SuppressWarnings("unchecked")
    public static RedisTemplate<String, Object> getTemplate() {
        return SpringContext.getBean(RedisTemplate.class);
    }
}
