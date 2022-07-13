package io.openjob.worker.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum TaskStatusEnum {

    /**
     * Unknown.
     */
    UNKNOWN(1, "unknown", false),

    /**
     * Init.
     */
    INIT(3, "init", false),

    /**
     * Pulled.
     */
    PULLED(5, "pulled", false),

    /**
     * running.
     */
    RUNNING(7, "running", false),

    /**
     * Success.
     */
    SUCCESS(9, "success", true),

    /**
     * Fail.
     */
    FAIL(11, "fail", true),
    ;

    /**
     * Status.
     */
    private final Integer status;

    /**
     * Message.
     */
    private final String message;

    /**
     * Finish.
     */
    private final Boolean finish;
}
