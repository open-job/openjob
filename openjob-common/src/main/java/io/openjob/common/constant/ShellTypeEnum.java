package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
@Getter
@AllArgsConstructor
public enum ShellTypeEnum {

    /**
     * unix
     */
    UNIX("unix"),

    /**
     * windows
     */
    WINDOWS("windows"),
    ;

    /**
     * Type
     */
    private final String type;
}
