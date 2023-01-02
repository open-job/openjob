package io.openjob.worker.exception;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class BatchUpdateStatusException extends RuntimeException{
    public BatchUpdateStatusException() {
    }

    public BatchUpdateStatusException(String message) {
        super(message);
    }

    public BatchUpdateStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public BatchUpdateStatusException(Throwable cause) {
        super(cause);
    }

    public BatchUpdateStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
