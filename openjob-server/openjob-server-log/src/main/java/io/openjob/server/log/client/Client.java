package io.openjob.server.log.client;

import java.io.IOException;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public interface Client {
    void connect() throws Exception;

    void shutdown() throws IOException;
}
