package io.openjob.common.util;

import java.util.Arrays;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public class ExceptionUtil {

    /**
     * Format stack trace
     *
     * @param e e
     * @return String
     */
    public static String formatStackTraceAsString(Throwable e) {
        StringBuffer sb = new StringBuffer();
        sb.append(e.getClass().getName()).append(": ").append(e.getMessage()).append("\n");
        Arrays.stream(e.getStackTrace()).forEach(s -> sb.append(s.toString()).append("\n"));
        return sb.toString();
    }
}
