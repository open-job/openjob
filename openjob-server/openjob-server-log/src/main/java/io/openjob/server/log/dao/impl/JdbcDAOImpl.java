package io.openjob.server.log.dao.impl;

import io.openjob.common.request.WorkerJobInstanceTaskLogFieldRequest;
import io.openjob.server.log.client.AbstractJdbcHikariClient;
import io.openjob.server.log.dao.LogDAO;
import io.openjob.server.log.entity.JobInstanceTaskLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class JdbcDAOImpl implements LogDAO {
    private final AbstractJdbcHikariClient jdbcHikariClient;

    public JdbcDAOImpl(AbstractJdbcHikariClient jdbcHikariClient) {
        this.jdbcHikariClient = jdbcHikariClient;
    }

    @Override
    public void batchAdd(List<JobInstanceTaskLog> jobInstanceTaskLogs) throws SQLException {
        String sql = "INSERT INTO job_instance_task_log ("
                + "job_id,"
                + "job_instance_id,"
                + "circle_id,"
                + "task_id,"
                + "task_unique_id,"
                + "worker_address,"
                + "content,"
                + "`time`"
                + ") VALUES (?, ?, ?, ?,?, ?, ?, ?)";

        PreparedStatement ps = null;
        try (Connection connection = this.jdbcHikariClient.getConnection()) {
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (JobInstanceTaskLog instanceTaskLog : jobInstanceTaskLogs) {
                ps.setLong(1, instanceTaskLog.getJobId());
                ps.setLong(2, instanceTaskLog.getJobInstanceId());
                ps.setLong(3, instanceTaskLog.getCircleId());
                ps.setLong(4, instanceTaskLog.getTaskId());
                ps.setString(5, instanceTaskLog.getTaskUniqueId());
                ps.setString(6, instanceTaskLog.getWorkerAddress());
                ps.setString(7, this.getContent(instanceTaskLog.getFields()));
                ps.setLong(8, instanceTaskLog.getTime());
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
    public List<JobInstanceTaskLog> queryByPage(String taskUniqueId, Long time, Long size) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM job_instance_task_log WHERE task_id=? AND time > ? limit ?";
        try (Connection connection = this.jdbcHikariClient.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, taskUniqueId);
            ps.setLong(2, time);
            ps.setLong(3, size);
            rs = ps.executeQuery();

            List<JobInstanceTaskLog> taskLogList = new ArrayList<>();
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

    private String getContent(List<WorkerJobInstanceTaskLogFieldRequest> fields) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (WorkerJobInstanceTaskLogFieldRequest f : fields) {
            if (Objects.isNull(f.getValue())) {
                continue;
            }

            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(System.getProperty(" "));
            }

            sb.append(f.getName());
            sb.append(":");
            sb.append(f.getValue());
        }

        return sb.toString();
    }

    private JobInstanceTaskLog convert(ResultSet rs) throws SQLException {
        JobInstanceTaskLog taskLog = new JobInstanceTaskLog();
        taskLog.setJobId(rs.getLong("job_id"));
        taskLog.setJobInstanceId(rs.getLong("job_instance_id"));
        taskLog.setCircleId(rs.getLong("circle_id"));
        taskLog.setTaskId(rs.getLong("task_id"));
        taskLog.setTaskUniqueId(rs.getString("task_unique_id"));
        taskLog.setWorkerAddress(rs.getString("worker_address"));
        taskLog.setContent(rs.getString("content"));
        taskLog.setTime(rs.getLong("time"));
        return taskLog;
    }
}
