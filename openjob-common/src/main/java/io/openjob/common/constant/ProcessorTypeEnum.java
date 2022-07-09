package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ProcessorTypeEnum {
    /**
     * Java.
     */
    JAVA(1, "java"),

    /**
     * Shell.
     */
    SHELL(3, "shell"),

    /**
     * Http.
     */
    HTTP(5, "http"),
    ;

    private final Integer type;
    private final String message;
}
