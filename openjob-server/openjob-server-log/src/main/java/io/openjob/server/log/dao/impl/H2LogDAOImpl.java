package io.openjob.server.log.dao.impl;

import io.openjob.server.log.client.JdbcHikariClient;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class H2LogDAOImpl extends JdbcDAOImpl {
    public H2LogDAOImpl(JdbcHikariClient jdbcHikariClient) {
        super(jdbcHikariClient);
    }
}
