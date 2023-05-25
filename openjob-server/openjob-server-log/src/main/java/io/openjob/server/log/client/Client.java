package io.openjob.server.log.client;

import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public interface Client extends InitializingBean {
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
