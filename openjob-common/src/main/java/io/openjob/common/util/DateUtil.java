package io.openjob.common.util;

import java.time.Instant;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class DateUtil {
    private DateUtil() {

    }

    /**
     * 当前时间(Instant)
     */
    public static Instant instant() {
        return Instant.now();
    }

    /**
     * 当前时间戳(s)
     */
    public static Integer now() {
        return (int) instant().getEpochSecond();
    }

    public static Long timestamp() {
        return instant().getEpochSecond();
    }

    /**
     * 当前毫秒时间(Long)
     */
    public static Long milliLongTime() {
        return instant().toEpochMilli();
    }
}
