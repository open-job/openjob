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

    public static String getDelayKey(String topic) {
        return String.format("%s:%s", CacheConst.DELAY_PREFIX, topic);
    }

    public static String getDetailKey(String taskId) {
        return String.format("%s:%s", CacheConst.DELAY_DETAIL_PREFIX, taskId);
    }

    public static String getZsetKey(Long slotId) {
        return String.format("%s:%d", CacheConst.DELAY_ZSET_PREFIX, slotId);
    }

    public static String getListKey(Long slotId) {
        return String.format("%s:%d", CacheConst.DELAY_LIST_PREFIX, slotId);
    }
}
