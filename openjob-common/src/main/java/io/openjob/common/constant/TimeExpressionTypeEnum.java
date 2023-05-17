package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TimeExpressionTypeEnum {

    /**
     * None for workflow.
     */
    NONE("none"),

    /**
     * Cron.
     */
    CRON("cron"),

    /**
     * Fixed rate.
     */
    FIXED_RATE("fixedRate"),

    /**
     * Second delay.
     */
    SECOND_DELAY("secondDelay"),

    /**
     * One time.
     */
    ONE_TIME("oneTime"),
    ;

    private final String type;

    public static final List<String> CRON_TYPES = Arrays.asList(CRON.type, ONE_TIME.type);

    public static Boolean isCron(String type) {
        return CRON.getType().equals(type);
    }

    public static Boolean isSecondDelay(String type) {
        return SECOND_DELAY.getType().equals(type);
    }

    public static Boolean isOneTime(String type) {
        return ONE_TIME.getType().equals(type);
    }
}
