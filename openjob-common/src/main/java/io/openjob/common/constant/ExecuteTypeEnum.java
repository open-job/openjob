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
    STANDALONE("standalone"),

    /**
     * Broadcast.
     */
    BROADCAST("broadcast"),

    /**
     * MapReduce.
     */
    MAP_REDUCE("mapReduce"),

    /**
     * Sharding.
     */
    Sharding("sharding"),
    ;

    private final String type;
}
