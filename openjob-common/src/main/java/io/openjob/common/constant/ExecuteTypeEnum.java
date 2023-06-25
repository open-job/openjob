package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
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
    SHARDING("sharding"),
    ;

    private final String type;

    /**
     * Whether is standalone
     *
     * @param type type
     * @return Boolean
     */
    public static Boolean isStandalone(String type) {
        return STANDALONE.getType().equals(type);
    }

    /**
     * Whether is broadcast
     *
     * @param type type
     * @return Boolean
     */
    public static Boolean isBroadcast(String type) {
        return BROADCAST.getType().equals(type);
    }


    /**
     * Whether is map reduce
     *
     * @param type type
     * @return Boolean
     */
    public static Boolean isMapReduce(String type) {
        return MAP_REDUCE.getType().equals(type);
    }

    /**
     * Whether is sharding
     *
     * @param type type
     * @return Boolean
     */
    public static Boolean isSharding(String type) {
        return SHARDING.getType().equals(type);
    }
}
