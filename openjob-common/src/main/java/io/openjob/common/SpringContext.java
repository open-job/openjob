package io.openjob.common;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

/**
 * @author stelin swoft@qq.com
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

    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static Boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }


    public static String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return applicationContext.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}