package io.openjob.server.log.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.log.client.AbstractJdbcHikariClient;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.dto.ProcessorLogDTO;
import io.openjob.server.log.dto.ProcessorLogFieldDTO;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class JdbcDAOImpl implements LogDAO {
    private final AbstractJdbcHikariClient jdbcHikariClient;

    public JdbcDAOImpl(AbstractJdbcHikariClient jdbcHikariClient) {
        this.jdbcHikariClient = jdbcHikariClient;
    }

    @Override
    public void batchAdd(List<ProcessorLogDTO> processorLogList) throws Exception {
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
            for (ProcessorLogDTO processorLog : processorLogList) {
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
    public List<ProcessorLogDTO> queryByScroll(String taskUniqueId, Long time, Integer size) throws Exception {
        ResultSet rs = null;
        String sql = "SELECT * FROM `processor_log` WHERE `task_id`=? AND `time` > ? ORDER BY `time` ASC limit ?";
        try (Connection connection = this.jdbcHikariClient.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, taskUniqueId);
            ps.setLong(2, time);
            ps.setLong(3, size);
            rs = ps.executeQuery();

            List<ProcessorLogDTO> taskLogList = new ArrayList<>();
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

    @Override
    public PageDTO<ProcessorLogDTO> queryByPageSize(String taskUniqueId, String searchKey, Integer page, Integer size) {
        return null;
    }

    @Override
    public void deleteByLastTime(Long lastTime) throws Exception {
        String sql = "delete from `processor_log` where `time` < ?";
        try (Connection connection = this.jdbcHikariClient.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lastTime);
            int logCount = ps.executeUpdate();
            log.info("System data clear success!logCount={}", logCount);
        }
    }

    private String getContent(List<ProcessorLogFieldDTO> fields) {
        Map<String, String> fieldMap = fields.stream()
                .collect(Collectors.toMap(ProcessorLogFieldDTO::getName, ProcessorLogFieldDTO::getValue));

        // Format log fields
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(fieldMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ProcessorLogDTO convert(ResultSet rs) throws SQLException {
        ProcessorLogDTO taskLog = new ProcessorLogDTO();
        taskLog.setTaskId(rs.getString("task_id"));
        taskLog.setWorkerAddress(rs.getString("worker_address"));
        taskLog.setTime(rs.getLong("time"));
        String content = rs.getString("content");

        // Parse log fields
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> fieldMap = mapper.readValue(content, new TypeReference<Map<String, String>>() {
            });

            List<ProcessorLogFieldDTO> fieldsList = new ArrayList<>();
            fieldMap.forEach((name, value) -> fieldsList.add(new ProcessorLogFieldDTO(name, value)));
            taskLog.setFields(fieldsList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return taskLog;
    }
}
