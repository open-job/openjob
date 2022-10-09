package io.openjob.server.scheduler.util;

import io.openjob.server.scheduler.constant.CacheConst;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class CacheUtil {
    public static String getTopicKey(String topic) {
        return String.format("%s:%s", CacheConst.TOPIC_PREFIX, topic);
    }
}
