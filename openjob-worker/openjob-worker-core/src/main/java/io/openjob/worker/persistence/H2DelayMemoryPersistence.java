package io.openjob.worker.persistence;

import io.openjob.common.util.DateUtil;
import io.openjob.worker.entity.Delay;
import lombok.extern.slf4j.Slf4j;

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
public class H2DelayMemoryPersistence implements DelayPersistence {
    /**
     * Connection pool.
     */
    private final H2ConnectionPool connectionPool;

    /**
     * New H2DelayMemoryPersistence
     */
    public H2DelayMemoryPersistence() {
        this.connectionPool = new H2ConnectionPool();

        try {
            this.initTable();
        } catch (Exception e) {
            log.error("H2MemoryPersistence initTable failed!", e);
        }
    }

    @Override
    public void initTable() throws Exception {
        String createSql = "CREATE TABLE IF NOT EXISTS `delay_worker` ("
                + "  `id` bigint(20) unsigned NOT NULL PRIMARY KEY,"
                + "  `topic` varchar(128) NOT NULL DEFAULT '',"
                + "  `pull_size` int(11) unsigned NOT NULL DEFAULT '0',"
                + "  `pull_time` bigint(16) unsigned NOT NULL DEFAULT '0',"
                + "  `create_time` bigint(12) unsigned NOT NULL,"
                + "  `update_time` bigint(12) unsigned NOT NULL,"
                + "  PRIMARY KEY (`id`)"
                + ")";

        try (Connection connection = this.connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(createSql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public Integer batchSave(List<Delay> delays) throws SQLException {
        String sql = "INSERT INTO delay_worker ("
                + "id,"
                + "topic,"
                + "pull_size,"
                + "pull_time,"
                + "create_time,"
                + "update_time"
                + ") VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try (Connection connection = this.connectionPool.getConnection()) {
            ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (Delay delay : delays) {
                ps.setLong(1, delay.getId());
                ps.setString(2, delay.getTopic());
                ps.setInt(3, delay.getPullSize());
                ps.setLong(4, delay.getPullTime());
                ps.setLong(5, delay.getCreateTime());
                ps.setLong(6, delay.getUpdateTime());
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
    public Integer updatePullSizeById(Long id, Integer size, Long updateTime) throws SQLException {
        String sql = "UPDATE delay_worker SET pull_size=pull_size+?, update_time=? WHERE id=?";

        PreparedStatement ps = null;
        try (Connection connection = this.connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql);
            ps.setInt(1, size);
            ps.setLong(2, updateTime);
            ps.setLong(3, id);
            ps.addBatch();

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
    public Integer batchUpdatePullTime(List<Delay> delays) throws SQLException {
        String sql = "UPDATE delay_worker SET pull_time=?,update_time=? WHERE id=?";
        PreparedStatement ps = null;
        try (Connection connection = this.connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql);
            for (Delay delay : delays) {
                ps.setLong(1, delay.getPullTime());
                ps.setLong(1, delay.getUpdateTime());
                ps.setLong(1, delay.getId());
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
    public List<Delay> findPullList() throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT id,pull_size FROM delay_worker WHERE delay_worker.pull_size > 0 AND pull_time <?";
        try (Connection connection = this.connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, DateUtil.milliLongTime());
            rs = ps.executeQuery();

            List<Delay> delayList = new ArrayList<>();
            while (rs.next()) {
                delayList.add(convert(rs));
            }
            return delayList;
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
    }

    @Override
    public Integer deleteByIds(List<Long> ids) throws SQLException {
        String sql = "DELETE FROM delay_worker WHERE id = ?";

        PreparedStatement ps = null;
        try (Connection connection = this.connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql);
            for (Long id : ids) {
                ps.setLong(1, id);
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

    /**
     * @param rs result set
     * @return Delay
     */
    private Delay convert(ResultSet rs) throws SQLException {
        Delay delay = new Delay();
        delay.setId(rs.getLong("id"));
        delay.setPullSize(rs.getInt("pull_size"));
        return delay;
    }
}
