package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TimeExpressionTypeEnum {

    /**
     * None for workflow.
     */
    NONE(1, "none"),

    /**
     * Cron.
     */
    CRON(3, "cron"),

    /**
     * Fixed rate.
     */
    FIXED_RATE(5, "fixedRate"),

    /**
     * Second delay.
     */
    SECOND_DELAY(7, "secondDelay"),

    /**
     * One time.
     */
    ONE_TIME(9, "oneTime"),
    ;

    private final Integer type;
    private final String message;

    public static final List<Integer> CRON_TYPES = Arrays.asList(CRON.type, FIXED_RATE.type, ONE_TIME.type);
}
