package io.openjob.worker.processor;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ProcessorHandlerMapping {
    private static final ConcurrentMap<String, ProcessorHandler> HANDLER_CONCURRENT_MAP = Maps.newConcurrentMap();

    public static void registerProcessorHandler(String name, ProcessorHandler processorHandler) {
        HANDLER_CONCURRENT_MAP.put(name, processorHandler);
    }

    public static ProcessorHandler getProcessorHandler(String name) {
        return HANDLER_CONCURRENT_MAP.get(name);
    }

    public static Boolean hasProcessorHandler(String name) {
        return HANDLER_CONCURRENT_MAP.containsKey(name);
    }
}
