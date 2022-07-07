package io.openjob.server.repository.constant;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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
     * Fixed rate.
     */
    FIXED_RATE(3, "fixedRate"),

    /**
     * Second delay.
     */
    SECOND_DELAY(5, "secondDelay"),

    /**
     * Delay.
     */
    DELAY(7, "delay"),
    ;

    private final Integer type;
    private final String message;

    public static final List<Integer> SECOND_TYPES = Lists.newArrayList(FIXED_RATE.type, SECOND_DELAY.type);
}
