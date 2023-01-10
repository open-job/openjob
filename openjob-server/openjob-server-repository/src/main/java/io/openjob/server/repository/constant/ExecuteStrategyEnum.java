package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ExecuteStrategyEnum {
    /**
     * Discard after task.
     */
    DISCARD(1, "discard"),

    /**
     * Overlay before task.
     */
    OVERLAY(3, "overlay"),

    /**
     * Concurrency.
     */
    CONCURRENCY(5, "concurrency"),
    ;

    private final Integer status;
    private final String message;
}
