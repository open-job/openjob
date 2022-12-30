package io.openjob.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

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
    UNKNOWN(1, "unknown"),

    /**
     * Init.
     */
    INIT(3, "init"),

    /**
     * Exception task will be redispatched!
     */
    EXCEPTION(3, "exception"),

    /**
     * running.
     */
    RUNNING(7, "running"),

    /**
     * Success.
     */
    SUCCESS(9, "success"),

    /**
     * Fail.
     */
    FAILED(11, "fail"),
    ;

    /**
     * Non finish status list.
     */
    public static final List<Integer> NON_FINISH_LIST = Arrays.asList(
            UNKNOWN.status,
            INIT.status,
            RUNNING.status
    );

    /**
     * All status.
     */
    public static final List<Integer> ALL = Arrays.asList(
            UNKNOWN.status,
            INIT.status,
            RUNNING.status,
            SUCCESS.status,
            FAILED.status
    );

    /**
     * Parse status.
     *
     * @param status status
     * @return TaskStatusEnum
     */
    public static TaskStatusEnum parse(Integer status) {
        return EnumSet.allOf(TaskStatusEnum.class).stream()
                .filter(s -> s.getStatus().equals(status))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("TaskStatusEnum parseValue(%s) failed!", status)));
    }

    /**
     * Status.
     */
    private final Integer status;

    /**
     * Message.
     */
    private final String message;
}
