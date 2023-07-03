package io.openjob.server.log.dao.impl;

import io.openjob.server.log.client.AbstractJdbcHikariClient;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
public class PostgresqlDAOImpl extends JdbcDAOImpl {
    public PostgresqlDAOImpl(AbstractJdbcHikariClient jdbcHikariClient) {
        super(jdbcHikariClient);
    }

    @Override
    protected String getBatchAddSql() {
        return "INSERT INTO \"processor_log\" ("
                + "\"task_id\","
                + "\"worker_address\","
                + "\"content\","
                + "\"time\""
                + ") VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getQueryByScrollSql() {
        return "SELECT * FROM \"processor_log\" WHERE \"task_id\"=? AND \"time\" > ? ORDER BY \"time\" ASC limit ?";
    }

    @Override
    protected String getDeleteSql() {
        return "delete from \"processor_log\" where \"time\" < ?";
    }
}
