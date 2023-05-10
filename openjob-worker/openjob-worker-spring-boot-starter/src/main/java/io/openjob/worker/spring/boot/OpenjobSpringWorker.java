package io.openjob.worker.spring.boot;

import io.openjob.common.SpringContext;
import io.openjob.worker.processor.ProcessResult;
import io.openjob.worker.processor.ProcessorHandler;
import io.openjob.worker.processor.ProcessorHandlerMapping;
import io.openjob.worker.spring.boot.annotation.Openjob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class OpenjobSpringWorker implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        if (Objects.isNull(SpringContext.getApplicationContext())) {
            return;
        }

        // All bean names.
        String[] beanNames = SpringContext.getBeanNamesForType(Object.class, false, true);
        for (String beanName : beanNames) {
            Object bean = SpringContext.getBean(beanName);

            // Find openjob annotation method
            Map<Method, Openjob> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        (MethodIntrospector.MetadataLookup<Openjob>) method -> AnnotatedElementUtils.findMergedAnnotation(method, Openjob.class)
                );
            } catch (Throwable ex) {
                log.error(String.format("Processor handler resolve failed! beanName=%s beanClass=%s", beanName, beanName.getClass()), ex);
            }

            // Not find annotation
            if (CollectionUtils.isEmpty(annotatedMethods)) {
                continue;
            }

            for (Map.Entry<Method, Openjob> entry : annotatedMethods.entrySet()) {
                Method method = entry.getKey();
                Openjob openjob = entry.getValue();

                if (Objects.isNull(openjob)) {
                    continue;
                }

                // Register processor handler
                this.registerProcessorHandler(bean, method, openjob);
            }
        }
    }

    /**
     * Register processor handler
     *
     * @param bean    bean
     * @param method  method
     * @param openjob openjob
     */
    private void registerProcessorHandler(Object bean, Method method, Openjob openjob) {
        // Processor handler
        ProcessorHandler processorHandler = new ProcessorHandler();
        processorHandler.setTarget(bean);

        // Validate name
        String name = openjob.value();
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException(String.format("Openjob name can not be empty! class=%s method=%s", bean.getClass(), method.getName()));
        }

        // Bean has existed
        if (SpringContext.containsBean(name)) {
            throw new RuntimeException(String.format("Bean of the openjob name has existed! class=%s method=%s name=%s", bean.getClass(), method.getName(), name));
        }

        // Has existed
        if (ProcessorHandlerMapping.hasProcessorHandler(name)) {
            throw new RuntimeException(String.format("Openjob name has existed! class=%s method=%s name=%s", bean.getClass(), method.getName(), name));
        }

        // Return type
        if (!method.getReturnType().isAssignableFrom(ProcessResult.class)) {
            throw new RuntimeException(String.format("Processor method must return ProcessResult! class=%s method=%s name=%s", bean.getClass(), method.getName(), name));
        }

        method.setAccessible(true);
        processorHandler.setProcessMethod(method);

        // Pre method
        String preMethodName = openjob.pre();
        if (!StringUtils.isEmpty(preMethodName)) {
            try {
                Method preMethod = bean.getClass().getMethod(preMethodName);
                preMethod.setAccessible(true);
                processorHandler.setPreMethod(preMethod);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(String.format("Pre method is undefined! method=%s", preMethodName), e);
            }
        }

        // Post method
        String postMethodName = openjob.post();
        if (!StringUtils.isEmpty(postMethodName)) {
            try {
                Method postMethod = bean.getClass().getMethod(postMethodName);
                postMethod.setAccessible(true);
                processorHandler.setPostMethod(postMethod);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(String.format("Post method is undefined! method=%s", preMethodName), e);
            }
        }

        // Stop method
        String stopMethodName = openjob.stop();
        if (!StringUtils.isEmpty(stopMethodName)) {
            try {
                Method stopMethod = bean.getClass().getMethod(stopMethodName);
                stopMethod.setAccessible(true);
                processorHandler.setStopMethod(stopMethod);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(String.format("Stop method is undefined! method=%s", preMethodName), e);
            }
        }

        ProcessorHandlerMapping.registerProcessorHandler(name, processorHandler);
    }
}
