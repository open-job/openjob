package io.openjob.server.common.util;

import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * @author inhere in.798@qq.com
 */
public class BeanMapperUtil {
    /**
     * copy source object to target object on source is non-null.
     *
     * @param source source
     * @param target target
     * @return target
     */
    public static <T> T mapObject(Object source, T target) {
        if (Objects.nonNull(source)) {
            BeanUtils.copyProperties(source, target);
        }
        return target;
    }

    /**
     * map source object to class. if source is null, will return null.
     *
     * @param source      source
     * @param targetClass targetClass
     * @param <T>         class
     * @return object or null
     */
    public static <T> T mapOrNull(Object source, Class<T> targetClass) {
        return map(source, targetClass, false);
    }

    /**
     * map source object to class. if source is null, will return new target object.
     *
     * @param source      source
     * @param targetClass targetClass
     * @param <T>         class
     * @return object or null
     */
    public static <T> T map(Object source, Class<T> targetClass) {
        return map(source, targetClass, true);
    }

    /**
     * map source object to class
     *
     * @param source      source
     * @param targetClass targetClass
     * @param <T>         class
     * @return object
     */
    public static <T> T map(Object source, Class<T> targetClass, Boolean newIt) {
        boolean srcIsNull = Objects.isNull(source);
        if (srcIsNull && !newIt) {
            return null;
        }

        T target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (!srcIsNull) {
            BeanUtils.copyProperties(source, target);
        }

        return target;
    }
}
