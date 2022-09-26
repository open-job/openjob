package io.openjob.server.log.dao.impl;

import io.openjob.server.log.client.JdbcHikariClient;
import org.springframework.stereotype.Component;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class MysqlLogDAOImpl extends JdbcDAOImpl {
    public MysqlLogDAOImpl(JdbcHikariClient jdbcHikariClient) {
        super(jdbcHikariClient);
    }
}
