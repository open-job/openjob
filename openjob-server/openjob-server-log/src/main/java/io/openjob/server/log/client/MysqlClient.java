package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;
import io.openjob.server.log.constant.LogJdbcDriverConstant;

import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class MysqlClient extends AbstractJdbcHikariClient {
    private final LogProperties.MysqlProperties mysqlProperties;

    public MysqlClient(LogProperties.MysqlProperties mysqlProperties) {
        this.mysqlProperties = mysqlProperties;

        // Driver
        LogProperties.JdbcProperties properties = mysqlProperties.getProperties();
        if (Objects.isNull(properties.getDriver())) {
            properties.setDriver(LogJdbcDriverConstant.MYSQL);
        }

        this.init(properties);
    }
}
