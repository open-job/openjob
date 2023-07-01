package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;
import io.openjob.server.log.constant.LogJdbcDriverConstant;

import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
public class PostgresqlClient extends AbstractJdbcHikariClient{
    private final LogProperties.PostgresqlProperties postgresqlProperties;

    public PostgresqlClient(LogProperties.PostgresqlProperties postgresqlProperties) {
        this.postgresqlProperties = postgresqlProperties;
    }

    @Override
    public void initTable() {

    }

    @Override
    public void afterPropertiesSet() {
        // Driver
        LogProperties.JdbcProperties properties = postgresqlProperties.getProperties();
        if (Objects.isNull(properties.getDriver())) {
            properties.setDriver(LogJdbcDriverConstant.POSTGRESQL);
        }

        this.init(properties);
    }
}
