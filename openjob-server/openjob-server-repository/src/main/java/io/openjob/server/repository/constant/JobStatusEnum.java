package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum JobStatusEnum {
    /**
     * Running.
     */
    RUNNING(1, "running"),

    /**
     * Stop.
     */
    STOP(2, "stop"),
    ;

    private final Integer status;
    private final String message;

    /**
     * Whether is running
     *
     * @param status status
     * @return Boolean
     */
    public static Boolean isRunning(Integer status) {
        return RUNNING.getStatus().equals(status);
    }
}
