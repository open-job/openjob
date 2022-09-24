package io.openjob.server.log.h2;

import com.zaxxer.hikari.HikariDataSource;
import io.openjob.server.log.autoconfigure.LogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class H2ConnectionPool {
    /**
     * Data source.
     */
    private final HikariDataSource dataSource = new HikariDataSource();

    @Autowired
    public H2ConnectionPool(LogProperties.H2StorageProperties h2Properties) {
        dataSource.setJdbcUrl(h2Properties.getUrl());
        dataSource.setUsername(h2Properties.getUser());
        dataSource.setPassword(h2Properties.getPassword());
        dataSource.setMinimumIdle(8);
        dataSource.setMaximumPoolSize(128);
        dataSource.setDriverClassName(h2Properties.getDriver());
    }

    /**
     * Get connection from data source.
     *
     * @return Connection
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
