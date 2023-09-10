package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.7
 */
@Getter
@AllArgsConstructor
public enum RequestTypeEnum {
    /**
     * Server
     */
    SERVER("server"),

    /**
     * Agent
     */
    AGENT("agent"),
    ;

    private final String type;
}
