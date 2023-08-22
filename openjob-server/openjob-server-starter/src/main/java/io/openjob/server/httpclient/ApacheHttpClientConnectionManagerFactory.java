package io.openjob.server.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.6
 */
public interface ApacheHttpClientConnectionManagerFactory {
    String HTTP_SCHEME = "http";

    /**
     * Scheme for HTTPS based communication.
     */
    String HTTPS_SCHEME = "https";

    /**
     * Creates a new {@link HttpClientConnectionManager}.
     *
     * @param disableSslValidation   If true, SSL validation will be disabled.
     * @param maxTotalConnections    The total number of connections.
     * @param maxConnectionsPerRoute The total number of connections per route.
     * @param timeToLive             The time a connection is allowed to exist.
     * @param timeUnit               The time unit for the time-to-live value.
     *                               manager.
     * @return A new {@link HttpClientConnectionManager}.
     */
    HttpClientConnectionManager newConnectionManager(boolean disableSslValidation,
                                                     int maxTotalConnections, int maxConnectionsPerRoute, long timeToLive,
                                                     TimeUnit timeUnit);
}
