package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
@Getter
@AllArgsConstructor
public enum FailStatusEnum {

    /**
     * Job or delay
     */
    EXECUTE_FAIL(1, "Execute fail"),

    /**
     * Job or delay
     */
    EXECUTE_TIMEOUT(5, "Execute timeout"),

    /**
     * Job
     */
    EXECUTE_DISCARD(10, "Execute discard"),

    /**
     * Job
     */
    EXECUTE_RETRY_TIMES(15, "Execute retry times"),

    /**
     * Delay
     */
    EXECUTE_IGNORE(20, "Execute ignore"),
    ;

    private final Integer status;
    private final String message;
}
