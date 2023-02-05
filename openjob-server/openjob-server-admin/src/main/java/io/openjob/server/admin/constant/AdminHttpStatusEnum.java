package io.openjob.server.admin.constant;

import io.openjob.server.common.exception.HttpStatusExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum AdminHttpStatusEnum implements HttpStatusExceptionAssert {

    /**
     * 401
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * 403
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * 404
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * 500
     */
    SERVER_ERROR(500, "'Internal Server Error'"),
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
