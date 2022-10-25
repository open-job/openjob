package io.openjob.server.log.client;

import java.io.IOException;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface Client {
    /**
     * Connect
     *
     * @throws Exception Exception
     */
    void connect() throws Exception;

    /**
     * Shutdown
     *
     * @throws IOException IOException
     */
    void shutdown() throws IOException;
}
