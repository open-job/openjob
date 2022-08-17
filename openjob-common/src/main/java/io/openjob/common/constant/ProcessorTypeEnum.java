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
    JAVA("java"),

    /**
     * Shell.
     */
    SHELL("shell"),

    /**
     * Http.
     */
    HTTP("http"),
    ;

    private final String type;
}
