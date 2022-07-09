package io.openjob.common.constant;

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
    BROADCAST(3, "broadcast"),

    /**
     * MapReduce.
     */
    MAP_REDUCE(5, "mapReduce"),

    /**
     * Sharding.
     */
    Sharding(7, "sharding"),
    ;

    private final Integer type;
    private final String message;
}
