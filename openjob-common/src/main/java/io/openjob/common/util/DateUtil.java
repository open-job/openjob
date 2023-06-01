package io.openjob.common.util;

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

    /**
     * Date formatter pattern
     */
    private final static DateTimeFormatter DATE_FORMATTER_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Hour formatter pattern
     */
    private final static DateTimeFormatter HOUR_FORMATTER_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Mill formatter pattern
     */
    private final static DateTimeFormatter MILL_FORMATTER_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

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
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.systemDefault());
        return localDateTime.format(MILL_FORMATTER_PATTERN);
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
     * Get now format date
     *
     * @return Integer
     */
    public static Integer getNowFormatDate() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant(), ZoneId.systemDefault());
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

    /**
     * Get zero timestamp
     *
     * @return Long
     */
    public static Long getZeroTimestamp() {
        return LocalDate.now()
                .atStartOfDay(ZoneOffset.ofHours(8))
                .toInstant()
                .getEpochSecond();
    }

    /**
     * Format date
     *
     * @param dateTime dateTime
     * @return String
     */
    public static String formatDatePattern(String dateTime) {
        LocalDate localDate = LocalDate.parse(dateTime, DATE_FORMATTER);
        return localDate.format(DATE_FORMATTER_PATTERN);
    }

    /**
     * Format  hour
     *
     * @param hourTime hourTime
     * @return String
     */
    public static String formatHourPattern(String hourTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(hourTime, HOUR_FORMATTER);
        return localDateTime.format(HOUR_FORMATTER_PATTERN);
    }
}
