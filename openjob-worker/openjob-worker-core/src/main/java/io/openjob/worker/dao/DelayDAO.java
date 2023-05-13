package io.openjob.worker.dao;

import io.openjob.common.util.DateUtil;
import io.openjob.worker.entity.Delay;
import io.openjob.worker.persistence.DelayPersistence;
import io.openjob.worker.persistence.H2DelayMemoryPersistence;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Slf4j
public class DelayDAO {
    public static final DelayDAO INSTANCE = new DelayDAO();

    private final DelayPersistence delayPersistence;

    private DelayDAO() {
        this.delayPersistence = new H2DelayMemoryPersistence();
    }

    /**
     * Batch save.
     *
     * @param delays delays
     * @return Integer
     */
    public Integer batchSave(List<Delay> delays) {
        try {
            Long now = DateUtil.timestamp();
            delays.forEach(d -> {
                d.setCreateTime(now);
                d.setUpdateTime(now);
            });

            return this.delayPersistence.batchSave(delays);
        } catch (SQLException e) {
            log.error("Delay batch add failed!", e);
            return 0;
        }
    }

    /**
     * Pull by id.
     *
     * @param id   id
     * @param size size
     * @return Integer
     */
    public Integer updatePullSizeById(Long id, Integer size) {
        try {
            return this.delayPersistence.updatePullSizeById(id, size, DateUtil.timestamp());
        } catch (SQLException e) {
            log.error("Delay update pull size failed!", e);
            return 0;
        }
    }

    /**
     * Batch update pull time.
     *
     * @param delays delays
     * @return Integer
     */
    public Integer batchUpdatePullTime(List<Delay> delays) {
        try {
            return this.delayPersistence.batchUpdatePullTime(delays);
        } catch (SQLException e) {
            log.error("Delay batch update pull time failed!", e);
            return 0;
        }
    }

    /**
     * Find pull list.
     *
     * @return List
     */
    public List<Delay> findPullList() {
        try {
            return this.delayPersistence.findPullList();
        } catch (SQLException e) {
            log.error("Delay find not pull list failed!", e);
            return Collections.emptyList();
        }
    }

    /**
     * Delete all.
     */
    public Integer deleteAll() {
        try {
            return this.delayPersistence.deleteAll();
        } catch (SQLException e) {
            log.error("Delay delete all topic list", e);
            return 0;
        }
    }
}
