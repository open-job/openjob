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
     * current timestamp long value.
     *
     * @return long
     */
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
