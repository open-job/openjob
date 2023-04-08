package io.openjob.worker.processor;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ProcessorHandlerMapping {
    private static final ConcurrentMap<String, ProcessorHandler> handlerConcurrentMap = Maps.newConcurrentMap();

    public static void registerProcessorHandler(String name, ProcessorHandler processorHandler) {
        handlerConcurrentMap.put(name, processorHandler);
    }

    public static ProcessorHandler getProcessorHandler(String name) {
        return handlerConcurrentMap.get(name);
    }

    public static Boolean hasProcessorHandler(String name) {
        return handlerConcurrentMap.containsKey(name);
    }
}
