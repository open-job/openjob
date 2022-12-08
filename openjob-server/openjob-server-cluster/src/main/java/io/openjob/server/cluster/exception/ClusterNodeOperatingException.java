package io.openjob.server.cluster.exception;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class ClusterNodeOperatingException extends RuntimeException {

    /**
     * New exception.
     *
     * @param message message
     */
    public ClusterNodeOperatingException(String message) {
        super(message);
    }
}
