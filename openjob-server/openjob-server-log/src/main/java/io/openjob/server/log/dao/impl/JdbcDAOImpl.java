package io.openjob.server.log.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.openjob.server.log.client.AbstractJdbcHikariClient;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLog;
import io.openjob.server.log.dto.ProcessorLogField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
public class JdbcDAOImpl implements LogDAO {
    private final AbstractJdbcHikariClient jdbcHikariClient;

    public JdbcDAOImpl(AbstractJdbcHikariClient jdbcHikariClient) {
        this.jdbcHikariClient = jdbcHikariClient;
    }

    @Override
    public void batchAdd(List<ProcessorLog> processorLogList) throws SQLException {
        String sql = "INSERT INTO `processor_log` ("
                + "`task_id`,"
                + "`worker_address`,"
                + "`content`,"
                + "`time`"
                + ") VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;
        try (Connection connection = this.jdbcHikariClient.getConnection()) {
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (ProcessorLog processorLog : processorLogList) {
                ps.setString(1, processorLog.getTaskId());
                ps.setString(2, processorLog.getWorkerAddress());
                ps.setString(3, this.getContent(processorLog.getFields()));
                ps.setLong(4, processorLog.getTime());
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
    public List<ProcessorLog> queryByPage(String taskUniqueId, Long time, Long size) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM `processor_log` WHERE `task_id`=? AND `time` > ? limit ?";
        try (Connection connection = this.jdbcHikariClient.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, taskUniqueId);
            ps.setLong(2, time);
            ps.setLong(3, size);
            rs = ps.executeQuery();

            List<ProcessorLog> taskLogList = new ArrayList<>();
            while (rs.next()) {
                taskLogList.add(convert(rs));
            }
            return taskLogList;
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
    }

    private String getContent(List<ProcessorLogField> fields) {
        Map<String, String> fieldMap = fields.stream()
                .collect(Collectors.toMap(ProcessorLogField::getName, ProcessorLogField::getValue));

        // Format log fields
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(fieldMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ProcessorLog convert(ResultSet rs) throws SQLException {
        ProcessorLog taskLog = new ProcessorLog();
        taskLog.setTaskId(rs.getString("task_id"));
        taskLog.setWorkerAddress(rs.getString("worker_address"));
        taskLog.setTime(rs.getLong("time"));
        String content = rs.getString("content");

        // Parse log fields
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> fieldMap = mapper.readValue(content, new TypeReference<Map<String, String>>() {
            });

            List<ProcessorLogField> fieldsList = new ArrayList<>();
            fieldMap.forEach((name, value) -> fieldsList.add(new ProcessorLogField(name, value)));
            taskLog.setFields(fieldsList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return taskLog;
    }
}
