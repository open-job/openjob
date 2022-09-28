package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class H2Client extends JdbcHikariClient {
    private final LogProperties.H2Properties h2Properties;

    public H2Client(LogProperties.H2Properties h2Properties) {
        this.h2Properties = h2Properties;

        // Init jdbc client
        this.init(h2Properties.getProperties());
    }

    @Override
    public void initTable() throws SQLException {
        String createSql = "CREATE TABLE IF NOT EXISTS `job_instance_task_log` (" +
                "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT," +
                "  `job_id` bigint(20) NOT NULL," +
                "  `job_instance_id` bigint(20) NOT NULL," +
                "  `circle_id` bigint(20) NOT NULL," +
                "  `task_id` bigint(20) NOT NULL," +
                "  `task_unique_id` varchar(64) NOT NULL DEFAULT ''," +
                "  `worker_address` varchar(128) NOT NULL DEFAULT ''," +
                "  `content` longtext NOT NULL," +
                "  `time` bigint(13) NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  KEY `idx_task_unique_id_time` (`task_unique_id`,`time`)" +
                ");";

        try (Connection connection = this.getConnection();
             PreparedStatement ps = connection.prepareStatement(createSql)) {
            ps.executeUpdate();
        }
    }
}


