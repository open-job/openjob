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
    UNIX("unix"),
    WINDOWS("windows"),
    ;

    /**
     * Type
     */
    private final String type;
}
