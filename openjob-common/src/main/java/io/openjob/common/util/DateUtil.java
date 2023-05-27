package io.openjob.common.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class DateUtil {

    /**
     * Date formatter
     */
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Hour formatter
     */
    private final static DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH");


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
     * Current mills(Long)
     */
    public static Long milliLongTime() {
        return instant().toEpochMilli();
    }

    /**
     * Date format
     *
     * @param timestamp timestamp
     * @return Integer
     */
    public static Integer formatDateByTimestamp(Long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        return Integer.valueOf(localDateTime.format(DATE_FORMATTER));
    }

    /**
     * Hour format
     *
     * @param timestamp timestamp
     * @return Integer
     */
    public static Integer formatHourByTimestamp(Long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        return Integer.valueOf(localDateTime.format(HOUR_FORMATTER));
    }

    public static Long getZeroTimestamp() {
        return LocalDate.now()
                .atStartOfDay(ZoneOffset.ofHours(8))
                .toInstant()
                .getEpochSecond();
    }
}
