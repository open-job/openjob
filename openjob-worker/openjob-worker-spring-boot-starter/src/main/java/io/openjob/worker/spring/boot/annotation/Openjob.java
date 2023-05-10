package io.openjob.worker.spring.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Openjob {

    /**
     * Job name
     *
     * @return String
     */
    String value();

    /**
     * Pre job
     *
     * @return String
     */
    String pre() default "";

    /**
     * Post job
     *
     * @return String
     */
    String post() default "";

    /**
     * Stop job
     *
     * @return String
     */
    String stop() default "";
}
