package io.openjob.worker.processor;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ProcessorHandlerMapping {
    private static final ConcurrentMap<String, ProcessorHandler> handlerConcurrentMap = Maps.newConcurrentMap();

    public static ProcessorHandler getProcessorHandler(String name) {
        return handlerConcurrentMap.get(name);
    }
}
