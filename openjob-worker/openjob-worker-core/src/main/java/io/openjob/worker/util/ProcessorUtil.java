package io.openjob.worker.util;

import io.openjob.worker.processor.BaseProcessor;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ProcessorUtil {
    /**
     * Get processor.
     *
     * @param className class name.
     * @return BaseProcessor
     */
    public static BaseProcessor getProcess(String className) {
        try {
            return (BaseProcessor) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
