package io.openjob.server.common.util;

import lombok.extern.log4j.Log4j2;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Log4j2
public class LogUtil {
    public static void logAndThrow(String message) {
        log.error(message);
        throw new RuntimeException(message);
    }
}
