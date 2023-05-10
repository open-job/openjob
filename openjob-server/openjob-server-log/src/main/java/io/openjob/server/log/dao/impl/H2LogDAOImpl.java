package io.openjob.server.log.dao.impl;

import io.openjob.server.log.client.H2Client;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class H2LogDAOImpl extends JdbcDAOImpl {

    public H2LogDAOImpl(H2Client h2Client) {
        super(h2Client);
    }
}
