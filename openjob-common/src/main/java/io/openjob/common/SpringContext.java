package io.openjob.common;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        SpringContext.applicationContext = context;
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }
}