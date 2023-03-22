package io.openjob.common.util;

import java.text.SimpleDateFormat;
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
     * Format timestamp
     *
     * @param timeMillis timeMillis
     * @return String
     */
    public static String formatTimestamp(Long timeMillis) {
        return timestampToString(timeMillis, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * Timestamp to string
     *
     * @param timeMillis timeMillis
     * @param pattern    pattern
     * @return String
     */
    public static String timestampToString(Long timeMillis, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(timeMillis);
    }

    /**
     * 当前毫秒时间(Long)
     */
    public static Long milliLongTime() {
        return instant().toEpochMilli();
    }
}
