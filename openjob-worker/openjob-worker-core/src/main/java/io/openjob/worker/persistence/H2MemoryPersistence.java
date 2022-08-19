package io.openjob.worker.persistence;

import io.openjob.worker.entity.Task;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class H2MemoryPersistence implements TaskPersistence {

    /**
     * Connection pool.
     */
    private final H2ConnectionPool connectionPool;

    /**
     * constructor.
     */
    public H2MemoryPersistence() {
        this.connectionPool = new H2ConnectionPool();

        try {
            this.initTable();
        } catch (Exception e) {
            log.error("H2MemoryPersistence initTable failed!", e);
        }
    }

    @Override
    public void initTable() throws Exception {
        String createSql = "CREATE TABLE IF NOT EXISTS `task` (" +
                "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT," +
                "  `job_id` bigint(20) NOT NULL," +
                "  `instance_id` bigint(20) NOT NULL," +
                "  `circle_id` bigint(20) NOT NULL DEFAULT '0'," +
                "  `task_id` varchar(64) NOT NULL DEFAULT ''," +
                "  `task_name` varchar(128) NOT NULL," +
                "  `task_parent_id` varchar(64) NOT NULL DEFAULT '0'," +
                "  `status` tinyint(2) NOT NULL DEFAULT '1'," +
                "  `worker_address` varchar(32) NOT NULL DEFAULT ''," +
                "  `task_body` blob," +
                "  `create_time` int(11) NOT NULL," +
                "  `update_time` int(11) NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE KEY `udx_task_id` (`task_id`)," +
                "  KEY `idx_instance_id_circle_id` (`instance_id`,`circle_id`)" +
                ")";

        try (Connection connection = this.connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(createSql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public Integer batchSave(List<Task> tasks) throws SQLException {
        String sql = "INSERT INTO task (" +
                "job_id," +
                "instance_id," +
                "circle_id," +
                "task_id," +
                "task_name," +
                "task_parent_id," +
                "status," +
                "worker_address," +
                "task_body," +
                "create_time," +
                "update_time" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = this.connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (Task task : tasks) {
                ps.setLong(1, task.getJobId());
                ps.setLong(2, task.getInstanceId());
                ps.setLong(3, task.getCircleId());
                ps.setString(4, task.getTaskId());
                ps.setString(5, task.getTaskName());
                ps.setString(6, task.getTaskParentId());
                ps.setInt(7, task.getStatus());
                ps.setString(8, task.getWorkerAddress());
                ps.setBytes(9, task.getTaskBody());
                ps.setInt(10, task.getCreateTime());
                ps.setInt(11, task.getUpdateTime());
                ps.addBatch();
            }
            int[] result = ps.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            ps.clearBatch();
            return result.length;
        }
    }

    @Override
    public Task findByTaskId(String taskId) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM task WHERE task_id=?";
        try (Connection connection = this.connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, taskId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return convert(rs);
            }

            return null;
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
    }

    /**
     * Convert result set to Task object.
     *
     * @param rs result set.
     * @return Task
     */
    private Task convert(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setJobId(rs.getLong("job_id"));
        task.setInstanceId(rs.getLong("instance_id"));
        task.setCircleId(rs.getLong("circle_id"));
        task.setTaskId(rs.getString("task_id"));
        task.setTaskName(rs.getString("task_name"));
        task.setTaskParentId(rs.getString("task_parent_id"));
        task.setStatus(rs.getInt("status"));
        task.setWorkerAddress(rs.getString("worker_address"));
        task.setTaskBody(rs.getBytes("task_body"));
        task.setCreateTime(rs.getInt("create_time"));
        task.setUpdateTime(rs.getInt("update_time"));
        return task;
    }
}
