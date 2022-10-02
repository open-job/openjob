package io.openjob.worker.persistence;

import io.openjob.worker.entity.Delay;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
        String createSql = "CREATE TABLE IF NOT EXISTS `delay` (" +
                "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "  `pull_time` int(11) NOT NULL DEFAULT '0'," +
                "  `create_time` int(11) NOT NULL," +
                "  `update_time` int(11) NOT NULL," +
                "  PRIMARY KEY (`id`)" +
                ")";

        try (Connection connection = this.connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(createSql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public Integer batchSave(List<Delay> delays) throws SQLException {
        return null;
    }

    @Override
    public Integer batchUpdatePullTimeById(List<Delay> delays) throws SQLException {
        return null;
    }

    @Override
    public List<Delay> findNotPullList() throws SQLException {
        return null;
    }

    @Override
    public Integer deleteByIds(List<Long> ids) throws SQLException {
        return null;
    }
}
