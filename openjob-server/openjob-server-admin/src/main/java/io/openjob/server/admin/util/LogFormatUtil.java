package io.openjob.server.admin.util;

import io.openjob.common.constant.LogFieldConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.log.dto.ProcessorLog;
import io.openjob.server.log.dto.ProcessorLogField;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class LogFormatUtil {

    public final static String LOG_FORMAT = "%-23s %-7s %-40s : %s";

    /**
     * Format show log message
     *
     * @param processorLog processorLog
     * @return String
     */
    public static String formatLog(ProcessorLog processorLog) {
        Map<String, String> fieldMap = processorLog.getFields().stream()
                .collect(Collectors.toMap(ProcessorLogField::getName, ProcessorLogField::getValue));
        String location = fieldMap.get(LogFieldConstant.LOCATION);
        return String.format(
                LOG_FORMAT,
                DateUtil.formatTimestamp(Long.parseLong(fieldMap.get(LogFieldConstant.TIME_STAMP))),
                fieldMap.get(LogFieldConstant.LEVEL),
                LogFormatUtil.formatLocation(location),
                fieldMap.get(LogFieldConstant.MESSAGE)
        );
    }

    /**
     * Format location
     *
     * @param location location
     * @return String
     */
    public static String formatLocation(String location) {
        if (StringUtils.isEmpty(location) || "-".equals(location)) {
            return "-";
        }

        String substring = location.substring(0, location.indexOf("("));
        String packageString = location.substring(0, substring.lastIndexOf("."));
        String lineNo = location.substring(location.lastIndexOf(":")).replace(")", "");

        int maxLen = 40 - lineNo.length();
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
