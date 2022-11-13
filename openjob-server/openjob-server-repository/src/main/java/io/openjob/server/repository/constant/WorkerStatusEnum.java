package io.openjob.server.repository.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum WorkerStatusEnum {
    /**
     * Online.
     */
    ONLINE(1, "online"),

    /**
     * Offline.
     */
    OFFLINE(2, "offline"),
    ;

    private final Integer status;
    private final String message;
}
