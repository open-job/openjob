package io.openjob.worker.persistence;

import io.openjob.common.constant.TaskStatusEnum;
import io.openjob.worker.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
@Slf4j
public class H2TaskMemoryPersistence implements TaskPersistence {

    /**
     * Connection pool.
     */
    private final H2ConnectionPool connectionPool;

    /**
     * constructor.
     */
    public H2TaskMemoryPersistence() {
        this.connectionPool = new H2ConnectionPool();

        try {
            this.initTable();
        } catch (Exception e) {
            log.error("H2MemoryPersistence initTable failed!", e);
        }
    }

    @Override
    public void initTable() throws Exception {
        String createSql = "CREATE TABLE IF NOT EXISTS `task` ("
                + "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                + "  `job_id` bigint(20) NOT NULL,"
                + "  `instance_id` bigint(20) NOT NULL,"
                + "  `circle_id` bigint(20) NOT NULL DEFAULT '0',"
                + "  `task_id` varchar(64) NOT NULL DEFAULT '',"
                + "  `task_name` varchar(128) NOT NULL DEFAULT '',"
                + "  `task_parent_id` varchar(64) NOT NULL DEFAULT '0',"
                + "  `status` tinyint(2) NOT NULL DEFAULT '1',"
                + "  `worker_address` varchar(64) NOT NULL DEFAULT '',"
                + "  `result` longtext,"
                + "  `task_body` blob,"
                + "  `create_time` int(11) NOT NULL,"
                + "  `update_time` int(11) NOT NULL,"
                + "  PRIMARY KEY (`id`),"
                + "  UNIQUE KEY `udx_task_id` (`task_id`),"
                + "  KEY `idx_instance_id_circle_id` (`instance_id`,`circle_id`)"
                + ")";

        try (Connection connection = this.connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(createSql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public Integer batchSave(List<Task> tasks) throws SQLException {
        String sql = "INSERT INTO task ("
                + "job_id,"
                + "instance_id,"
                + "circle_id,"
                + "task_id,"
                + "task_name,"
                + "task_parent_id,"
                + "status,"
                + "worker_address,"
                + "result,"
                + "task_body,"
                + "create_time,"
                + "update_time"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try (Connection connection = this.connectionPool.getConnection()) {
            ps = connection.prepareStatement(sql);
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
                ps.setString(9, task.getResult());
                ps.setBytes(10, task.getTaskBody());
                ps.setLong(11, task.getCreateTime());
                ps.setLong(12, task.getUpdateTime());
                ps.addBatch();
            }
            int[] result = ps.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            ps.clearBatch();
            return result.length;
        } finally {
            if (Objects.nonNull(ps)) {
                ps.close();
            }
        }
    }

    @Override
    public Task findByTaskId(String taskId) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM task WHERE task_id=?";
        try (Connection connection = this.connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
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

    @Override
    public Integer batchDeleteByTaskIds(List<String> taskIds) throws SQLException {
        String sql = "DELETE FROM task WHERE task_id = ?";

        PreparedStatement ps = null;
        try (Connection connection = this.connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql);
            for (String taskId : taskIds) {
                ps.setString(1, taskId);
                ps.addBatch();
            }

            int[] counts = ps.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            return counts.length;
        } finally {
            if (Objects.nonNull(ps)) {
                ps.close();
            }
        }
    }

    @Override
    public Integer batchUpdateStatusByTaskId(List<Task> tasks, Integer currentStatus) throws SQLException {
        // IsRunning
        boolean isRunning = TaskStatusEnum.RUNNING.getStatus().equals(currentStatus);

        // Running
        String sql = "UPDATE task SET status=? WHERE task_id=? AND status=?";

        // Not running.
        if (!isRunning) {
            sql = "UPDATE task SET status=? WHERE task_id=?";
        }

        PreparedStatement ps = null;
        try (Connection connection = this.connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql);
            for (Task task : tasks) {
                ps.setInt(1, task.getStatus());
                ps.setString(2, task.getTaskId());

                // Update to running must be init status.
                if (isRunning) {
                    ps.setInt(3, TaskStatusEnum.INIT.getStatus());
                }
                ps.addBatch();
            }

            int[] result = ps.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            return result.length;
        } finally {
            if (Objects.nonNull(ps)) {
                ps.close();
            }
        }
    }

    @Override
    public Integer countTask(Long instanceId, Long circleId, List<Integer> statusList) throws SQLException {
        String statusStr = StringUtils.join(statusList, ",");
        String sql = String.format("SELECT COUNT(*) FROM task WHERE instance_id=? AND circle_id=? AND status IN (%s)", statusStr);

        ResultSet rs = null;
        try (Connection connection = this.connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, instanceId);
            ps.setLong(2, circleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
    }

    @Override
    public List<Task> findListByPageSize(Long instanceId, Long circleId, Long size) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM task WHERE instance_id=? AND circle_id=? LIMIT ?";
        try (Connection connection = this.connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, instanceId);
            ps.setLong(2, circleId);
            ps.setLong(3, size);
            rs = ps.executeQuery();

            List<Task> taskList = new ArrayList<>();
            while (rs.next()) {
                taskList.add(convert(rs));
            }
            return taskList;
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
        task.setResult(rs.getString("result"));
        task.setWorkerAddress(rs.getString("worker_address"));
        task.setTaskBody(rs.getBytes("task_body"));
        task.setCreateTime(rs.getLong("create_time"));
        task.setUpdateTime(rs.getLong("update_time"));
        return task;
    }
}
