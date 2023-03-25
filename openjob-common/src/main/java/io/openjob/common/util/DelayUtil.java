package io.openjob.common.util;

import io.openjob.common.constant.DelayConstant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DelayUtil {

    /**
     * Get fail delay topic
     * @param topic topic
     * @return String
     */
    public static String getFailDelayTopic(String topic) {
        return String.format("%s.%s", topic, DelayConstant.FAIL_DELAY_SUFFIX);
    }
}
