package io.openjob.worker.util;

import io.openjob.common.OpenjobSpringContext;
import io.openjob.worker.processor.BaseProcessor;
import io.openjob.worker.processor.ProcessorHandler;
import io.openjob.worker.processor.ProcessorHandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import io.openjob.worker.processor.ShellProcessor;
import io.openjob.worker.processor.KettleProcessor;

import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class ProcessorUtil {
    /**
     * Get processor.
     *
     * @param className class name.
     * @return BaseProcessor
     */
    public static ProcessorHandler getProcessor(String className) {
        // Spring context
        ProcessorHandler processorHandler = null;
        if (Objects.nonNull(OpenjobSpringContext.getApplicationContext())) {
            try {
                // Bean name
                Object obj = OpenjobSpringContext.getBean(className);
                if (obj instanceof BaseProcessor) {
                    return new ProcessorHandler((BaseProcessor) obj);
                }
            } catch (Throwable ex) {
                // @Openjob annotation for method
                processorHandler = ProcessorHandlerMapping.getProcessorHandler(className);
            }
        }

        // Not find from spring context
        if (Objects.nonNull(processorHandler)) {
            return processorHandler;
        }

        try {
            // Load from class name.
            Object o = Class.forName(className).getDeclaredConstructor().newInstance();
            if (o instanceof BaseProcessor) {
                return new ProcessorHandler((BaseProcessor) o);
            }
        } catch (Throwable e) {
            log.error(String.format("Processor load failed! processor=%s", className), e);
        }
        return null;
    }

    /**
     * Get default processor
     *
     * @param processorType processorType
     * @return ProcessorHandler
     * @see ShellProcessor
     * @see KettleProcessor
     */
    public static ProcessorHandler getDefaultProcessor(String processorType) {
        try {
            String className = String.format("io.openjob.worker.processor.%sProcessor", StringUtils.capitalize(processorType));
            @SuppressWarnings("unchecked")
            Constructor<? extends BaseProcessor> constructor = ((Class<? extends BaseProcessor>) Class.forName(className))
                    .getConstructor();
            BaseProcessor baseProcessor = constructor.newInstance();
            return new ProcessorHandler(baseProcessor);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
