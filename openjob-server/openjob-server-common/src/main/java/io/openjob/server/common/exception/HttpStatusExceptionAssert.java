package io.openjob.server.common.exception;

import java.text.MessageFormat;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface HttpStatusExceptionAssert extends BaseExceptionAssert {

    /**
     * New exception.
     *
     * @param args args
     * @return RuntimeException
     */
    default RuntimeException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new HttpStatusException(this, args, msg);
    }

    /**
     * New exception.
     *
     * @param t    exception
     * @param args args
     * @return RuntimeException
     */
    default RuntimeException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new HttpStatusException(this, args, msg, t);
    }
}
