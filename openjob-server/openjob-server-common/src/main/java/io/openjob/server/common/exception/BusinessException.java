package io.openjob.server.common.exception;

/**
 * @author inhere
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
