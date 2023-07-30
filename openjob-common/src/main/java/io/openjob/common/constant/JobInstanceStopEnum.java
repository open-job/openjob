package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@AllArgsConstructor
@Getter
public enum JobInstanceStopEnum {

    /**
     * Normal stop
     */
    NORMAL(1, "Normal stop"),

    /**
     * Timout stop
     */
    TIMEOUT(2, "Timeout stop"),
    ;

    private final Integer type;
    private final String message;

    public static Boolean isNormal(Integer type) {
        return NORMAL.getType().equals(type);
    }

    public static Boolean isTimeout(Integer type) {
        return TIMEOUT.getType().equals(type);
    }
}
