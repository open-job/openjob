package io.openjob.server.log.client;

import io.openjob.server.log.autoconfigure.LogProperties;
import io.openjob.server.log.constant.LogJdbcDriverConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class H2Client extends AbstractJdbcHikariClient {
    private final LogProperties.H2Properties h2Properties;

    /**
     * New h2 client.
     *
     * @param h2Properties h2 properties
     */
    public H2Client(LogProperties.H2Properties h2Properties) {
        this.h2Properties = h2Properties;
    }

    @Override
    public void afterPropertiesSet() {
        // Driver
        LogProperties.JdbcProperties properties = h2Properties.getProperties();
        if (Objects.isNull(properties.getDriver())) {
            properties.setDriver(LogJdbcDriverConstant.H2);
        }

        // Init jdbc client
        this.init(properties);
    }

    @Override
    public void initTable() throws SQLException {
        String createSql = "CREATE TABLE IF NOT EXISTS `processor_log` ("
                + "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,"
                + "  `task_id` varchar(128) NOT NULL DEFAULT '',"
                + "  `worker_address` varchar(128) NOT NULL DEFAULT '',"
                + "  `content` longtext NOT NULL,"
                + "  `time` bigint(16) NOT NULL,"
                + "  PRIMARY KEY (`id`),"
                + "  KEY `idx_task_id_time` (`task_id`,`time`)"
                + ");";

        try (Connection connection = this.getConnection(); PreparedStatement ps = connection.prepareStatement(createSql)) {
            ps.executeUpdate();
        }
    }
}


