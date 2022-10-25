package io.openjob.server.log.client;

import com.zaxxer.hikari.HikariDataSource;
import io.openjob.server.log.autoconfigure.LogProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public abstract class AbstractJdbcHikariClient implements Client {
    /**
     * Data source.
     */
    private final HikariDataSource dataSource = new HikariDataSource();

    /**
     * Init
     * @param properties properties
     */
    public void init(LogProperties.JdbcProperties properties) {
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUser());
        dataSource.setPassword(properties.getPassword());
        dataSource.setMinimumIdle(2);
        dataSource.setMaximumPoolSize(8);
        dataSource.setDriverClassName(properties.getDriver());

        // Init table
        try {
            this.initTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Init table.
     * @throws SQLException SQLException
     */
    public void initTable() throws SQLException {
        String createSql = "CREATE TABLE IF NOT EXISTS `job_instance_task_log` ("
                + "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,"
                + "  `job_id` bigint(20) NOT NULL,"
                + "  `job_instance_id` bigint(20) NOT NULL,"
                + "  `circle_id` bigint(20) NOT NULL,"
                + "  `task_id` bigint(20) NOT NULL,"
                + "  `task_unique_id` varchar(64) NOT NULL DEFAULT '',"
                + "  `worker_address` varchar(128) NOT NULL DEFAULT '',"
                + "  `content` longtext NOT NULL,"
                + "  `time` bigint(13) NOT NULL,"
                + "  PRIMARY KEY (`id`)," + "  KEY `idx_task_unique_id_time` (`task_unique_id`,`time`)"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

        try (Connection connection = this.getConnection(); PreparedStatement ps = connection.prepareStatement(createSql)) {
            ps.executeUpdate();
        }
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
