package io.openjob.server.repository.util;

import io.openjob.server.repository.constant.CacheConstant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class CacheUtil {
    public static String getDelayKey(String topic) {
        return String.format("%s:%s", CacheConstant.DELAY_PREFIX, topic);
    }
}
