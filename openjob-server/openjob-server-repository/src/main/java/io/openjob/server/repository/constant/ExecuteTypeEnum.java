package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ExecuteTypeEnum {
    /**
     * Standalone.
     */
    STANDALONE(1, "standalone"),

    /**
     * Broadcast.
     */
    BROADCAST(2, "broadcast"),

    /**
     * MapReduce.
     */
    MAP_REDUCE(3, "mapReduce"),
    ;

    private final Integer type;
    private final String message;
}
