package io.openjob.server.log.dao.impl;

import io.openjob.server.log.client.AbstractJdbcHikariClient;
import io.openjob.server.log.dto.ProcessorLogDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.4
 */
public class OracleDAOImpl extends JdbcDAOImpl {
    private final ConcurrentLinkedQueue<Long> idLinkedQueue = new ConcurrentLinkedQueue<>();

    public OracleDAOImpl(AbstractJdbcHikariClient jdbcHikariClient) {
        super(jdbcHikariClient);
    }

    @Override
    public void batchAdd(List<ProcessorLogDTO> processorLogList) throws Exception {
        String sql = "INSERT INTO \"processor_log\" ("
                + "\"id\","
                + "\"task_id\","
                + "\"worker_address\","
                + "\"content\","
                + "\"time\""
                + ") VALUES (?,?, ?, ?, ?)";

        PreparedStatement ps = null;
        try (Connection connection = this.jdbcHikariClient.getConnection()) {
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (ProcessorLogDTO processorLog : processorLogList) {
                ps.setLong(1, this.getInsertId());
                ps.setString(2, processorLog.getTaskId());
                ps.setString(3, processorLog.getWorkerAddress());
                ps.setString(4, this.getContent(processorLog.getFields()));
                ps.setLong(5, processorLog.getTime());
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            ps.clearBatch();
        } finally {
            if (Objects.nonNull(ps)) {
                ps.close();
            }
        }
    }

    @Override
    protected String getQueryByScrollSql() {
        return "SELECT * FROM \"processor_log\" WHERE \"task_id\"=? AND \"time\" > ? AND rownum< ? ORDER BY \"time\" ASC";
    }

    @Override
    protected String getDeleteSql() {
        return "delete from \"processor_log\" where \"time\" < ?";
    }

    public Long getInsertId() throws Exception {
        Long id = this.idLinkedQueue.poll();
        if (Objects.nonNull(id)) {
            return id;
        }

        return nextIds();
    }

    public synchronized Long nextIds() throws Exception {
        PreparedStatement ps = null;
        try (Connection connection = this.jdbcHikariClient.getConnection()) {
            String sql = "SELECT processor_log_id.nextval FROM (select level from dual connect by level <= ?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, 500);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                this.idLinkedQueue.add(resultSet.getLong(1));
            }
        } finally {
            if (Objects.nonNull(ps)) {
                ps.close();
            }
        }

        return this.idLinkedQueue.poll();
    }
}
