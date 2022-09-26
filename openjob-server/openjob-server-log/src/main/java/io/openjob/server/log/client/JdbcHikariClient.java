package io.openjob.server.log.client;

import com.zaxxer.hikari.HikariDataSource;
import io.openjob.server.log.autoconfigure.LogProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JdbcHikariClient implements Client {
    /**
     * Data source.
     */
    private final HikariDataSource dataSource = new HikariDataSource();

    public void init(LogProperties.JdbcProperties properties) {
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUser());
        dataSource.setPassword(properties.getPassword());
        dataSource.setMinimumIdle(2);
        dataSource.setMaximumPoolSize(8);
        dataSource.setDriverClassName(properties.getDriver());
    }

    /**
     * Get connection from data source.
     *
     * @return Connection
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void connect() throws Exception {

    }

    @Override
    public void shutdown() throws IOException {

    }
}
