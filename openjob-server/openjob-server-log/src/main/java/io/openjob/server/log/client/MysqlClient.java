package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class MysqlClient extends JdbcHikariClient {
    private final LogProperties.MysqlProperties mysqlProperties;

    public MysqlClient(LogProperties.MysqlProperties mysqlProperties) {
        this.mysqlProperties = mysqlProperties;
        this.init(mysqlProperties.getProperties());
    }
}
