package io.openjob.worker.persistence;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class H2ConnectionPool {
    private HikariDataSource dataSource = new HikariDataSource();

    public H2ConnectionPool() {
        dataSource.setJdbcUrl("jdbc:h2:mem:openjob;AUTO_RECONNECT=TRUE;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setMinimumIdle(8);
        dataSource.setMaximumPoolSize(32);
        dataSource.setDriverClassName("org.h2.Driver");
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
