package io.openjob.worker.persistence;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class H2ConnectionPool {

    /**
     * Data source.
     */
    private final HikariDataSource dataSource = new HikariDataSource();

    /**
     * constructor.
     */
    public H2ConnectionPool() {
        dataSource.setJdbcUrl("jdbc:h2:mem:openjob;AUTO_RECONNECT=TRUE;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;WRITE_DELAY=0;");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setMinimumIdle(8);
        dataSource.setMaximumPoolSize(128);
        dataSource.setDriverClassName("org.h2.Driver");
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
