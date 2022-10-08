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
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
public class DelayDAO {
    public static final DelayDAO INSTANCE = new DelayDAO();

    private final DelayPersistence delayPersistence;

    private DelayDAO() {
        this.delayPersistence = new H2DelayMemoryPersistence();
    }

    public Integer batchSave(List<Delay> delays) {
        try {
            int now = DateUtil.now();
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

    public Integer updatePullSizeById(Long id, Integer size) {
        try {
            return this.delayPersistence.updatePullSizeById(id, size, DateUtil.now());
        } catch (SQLException e) {
            log.error("Delay batch update failed!", e);
            return 0;
        }
    }

    public List<Delay> findPullList() {
        try {
            return this.delayPersistence.findPullList();
        } catch (SQLException e) {
            log.error("Delay find not pull list failed!", e);
            return Collections.emptyList();
        }
    }

    public Integer deleteByIds(List<Long> ids) {
        try {
            return this.delayPersistence.deleteByIds(ids);
        } catch (SQLException e) {
            log.error("Delay delete ids failed!", e);
            return 0;
        }
    }
}
