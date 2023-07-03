package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ProcessorTypeEnum {
    /**
     * Processor(Java/Go/PHP)
     */
    PROCESSOR("processor"),

    /**
     * Shell.
     */
    SHELL("shell"),

    /**
     * Kettle.
     */
    KETTLE("kettle"),

    /**
     * Http.
     */
    HTTP("http"),
    ;

    private final String type;

    public static Boolean isProcessor(String type) {
        return ProcessorTypeEnum.PROCESSOR.getType().equals(type);
    }

    public static Boolean isShell(String type) {
        return ProcessorTypeEnum.SHELL.getType().equals(type);
    }

    public static Boolean isKettle(String type) {
        return ProcessorTypeEnum.KETTLE.getType().equals(type);
    }

    public static Boolean isHttp(String type) {
        return ProcessorTypeEnum.HTTP.getType().equals(type);
    }
}
