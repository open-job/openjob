package io.openjob.common.util;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class RuntimeUtil {
    /**
     * Get available processors.
     *
     * @return Integer
     */
    public static Integer processors() {
        return Runtime.getRuntime().availableProcessors();
    }
}
