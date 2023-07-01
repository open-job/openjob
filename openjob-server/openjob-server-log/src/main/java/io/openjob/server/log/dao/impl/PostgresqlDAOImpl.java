package io.openjob.server.log.dao.impl;

import io.openjob.server.log.client.AbstractJdbcHikariClient;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
public class PostgresqlDAOImpl extends JdbcDAOImpl{
    public PostgresqlDAOImpl(AbstractJdbcHikariClient jdbcHikariClient) {
        super(jdbcHikariClient);
    }
}
