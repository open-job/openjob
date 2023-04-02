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
    INIT(5, "init"),

    /**
     * Failover task will be redispatched!
     */
    FAILOVER(10, "failover"),

    /**
     * running.
     */
    RUNNING(15, "running"),

    /**
     * Fail.
     */
    FAILED(20, "fail"),

    /**
     * Success.
     */
    SUCCESS(25, "success"),

    /**
     * Stop.
     */
    STOP(30, "stop"),
    ;

    /**
     * Non finish status list.
     */
    public static final List<Integer> NON_FINISH_LIST = Arrays.asList(
            UNKNOWN.status,
            INIT.status,
            FAILOVER.status,
            RUNNING.status
    );

    /**
     * Complete status
     */
    public static final List<Integer> FINISH_LIST = Arrays.asList(
            FAILED.status,
            SUCCESS.status,
            STOP.status
    );

    /**
     * All status.
     */
    public static final List<Integer> ALL = Arrays.asList(
            UNKNOWN.status,
            INIT.status,
            FAILOVER.status,
            RUNNING.status,
            FAILED.status,
            SUCCESS.status,
            STOP.status
    );

    /**
     * Delay complete status
     */
    public static final List<Integer> DELAY_COMPLETE = Arrays.asList(
            TaskStatusEnum.SUCCESS.getStatus(),
            TaskStatusEnum.STOP.getStatus()
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

    public static Boolean isRunning(Integer status) {
        return RUNNING.getStatus().equals(status);
    }

    public static Boolean isSuccess(Integer status) {
        return SUCCESS.getStatus().equals(status);
    }

    public static Boolean isDelayComplete(Integer status) {
        return DELAY_COMPLETE.contains(status);
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
