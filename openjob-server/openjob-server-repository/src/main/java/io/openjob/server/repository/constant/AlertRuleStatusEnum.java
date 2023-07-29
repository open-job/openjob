package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.5
 */
@Getter
@AllArgsConstructor
public enum AlertRuleStatusEnum {
    /**
     * Turn on.
     */
    ON(1, "on"),

    /**
     * Turn off.
     */
    OFF(2, "off"),
    ;

    private final Integer status;
    private final String message;

    /**
     * Whether is turn on
     *
     * @param status status
     * @return Boolean
     */
    public static Boolean isOn(Integer status) {
        return ON.getStatus().equals(status);
    }
}
