package io.openjob.server.admin.constant;

import io.openjob.server.common.exception.CodeExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User: 100+
 * Namespace: 200+
 * Application: 300+
 * Job: 400+
 * Delay: 500+
 *
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum CodeEnum implements CodeExceptionAssert {
    // Code list
    USER_EXIST(100, "User is exist!"),

    // Namespace
    NAMESPACE_DELETE_INVALID(200, "Namespace can not be delete!"),

    // Application
    APP_NAME_EXIST(300, "App name must be globally unique!"),
    APP_DELETE_INVALID(301, "Application can not be deleted!"),

    // Job
    TIME_EXPRESSION_INVALID(400, "Time expression is invalid"),
    JOB_DELETE_INVALID(401, "Job can not be deleted!"),

    // Delay
    DELAY_TOPIC_EXIST(500, "Topic is exist!"),
    DELAY_DELETE_INVALID(501, "Delay can not be deleted!"),
    ;


    /**
     * Value
     */
    private final Integer value;

    /**
     * Message
     */
    private final String message;
}
