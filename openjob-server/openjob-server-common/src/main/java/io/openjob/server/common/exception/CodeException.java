package io.openjob.server.common.exception;

import io.openjob.server.common.constant.BaseEnum;
import lombok.Getter;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Getter
public class CodeException extends RuntimeException {
    /**
     * Base enum
     */
    private final BaseEnum baseEnum;

    /**
     * Args.
     */
    private final Object[] args;

    /**
     * New exception.
     *
     * @param baseEnum baseEnum
     * @param args     args
     * @param message  message
     */
    public CodeException(BaseEnum baseEnum, Object[] args, String message) {
        super(message);

        this.args = args;
        this.baseEnum = baseEnum;
    }

    /**
     * New exception.
     *
     * @param baseEnum baseEnum
     * @param args     args
     * @param message  message
     * @param cause    cause
     */
    public CodeException(BaseEnum baseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);

        this.args = args;
        this.baseEnum = baseEnum;
    }
}
