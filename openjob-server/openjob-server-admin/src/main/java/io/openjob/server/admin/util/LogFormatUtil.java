package io.openjob.server.admin.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class LogFormatUtil {

    /**
     * Format location
     *
     * @param location location
     * @return String
     */
    public static String formatLocation(String location) {
        String substring = location.substring(0, location.indexOf("("));
        String packageString = location.substring(0, substring.lastIndexOf("."));
        String lineNo = location.substring(location.lastIndexOf(":")).replace(")", "");

        int maxLen = 40;
        int len = packageString.length();
        if (len < maxLen) {
            return location;
        }

        int currentLen = len;
        String[] split = packageString.split("\\.");
        String[] format = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            String splitString = split[i];
            if (currentLen > maxLen) {
                int splitLen = splitString.length();
                format[i] = splitString.substring(0, 1);
                currentLen = currentLen - (splitLen - 1);
            } else {
                format[i] = splitString;
            }
        }
        return String.format("%s%s", StringUtils.join(format, "."), lineNo);
    }
}
