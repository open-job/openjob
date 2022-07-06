package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TimeExpressionTypeEnum {
    /**
     * Cron.
     */
    cron(1, "cron"),

    /**
     * Second.
     */
    SECOND(2, "second"),

    /**
     * Delay.
     */
    DELAY(3, "delay"),
    ;

    private final Integer type;
    private final String message;
}
