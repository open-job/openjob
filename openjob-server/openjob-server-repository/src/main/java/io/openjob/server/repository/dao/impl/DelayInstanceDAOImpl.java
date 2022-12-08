package io.openjob.server.repository.dao.impl;

import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.repository.repository.DelayInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayInstanceDAOImpl implements DelayInstanceDAO {

    private final DelayInstanceRepository delayInstanceRepository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DelayInstanceDAOImpl(DelayInstanceRepository delayInstanceRepository, JdbcTemplate jdbcTemplate) {
        this.delayInstanceRepository = delayInstanceRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer batchSave(List<DelayInstance> delayInstanceList) {
        String sql = "INSERT INTO `delay_instance` ("
                + "`namespace_id`, "
                + "`app_id`, "
                + "`task_id`, "
                + "`topic`, "
                + "`delay_id`, "
                + "`delay_params`, "
                + "`delay_extra`, "
                + "`status`, "
                + "`execute_time`, "
                + "`deleted`, "
                + "`delete_time`, "
                + "`create_time`, "
                + "`update_time`) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int[] ints = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@Nonnull PreparedStatement ps, int i) throws SQLException {
                DelayInstance d = delayInstanceList.get(i);
                ps.setLong(1, d.getNamespaceId());
                ps.setLong(2, d.getAppId());
                ps.setString(3, d.getTaskId());
                ps.setString(4, d.getTopic());
                ps.setLong(5, d.getDelayId());
                ps.setString(6, d.getDelayParams());
                ps.setString(7, d.getDelayExtra());
                ps.setInt(8, d.getStatus());
                ps.setLong(9, d.getExecuteTime());
                ps.setInt(10, d.getDeleted());
                ps.setLong(11, d.getDeleteTime());
                ps.setLong(12, d.getCreateTime());
                ps.setLong(13, d.getUpdateTime());
            }

            @Override
            public int getBatchSize() {
                return delayInstanceList.size();
            }
        });

        return ints.length;
    }

    @Override
    public List<DelayInstance> listDelayInstance(List<Long> slotIds, Integer time, Integer size) {
        DelayInstance delayInstance = new DelayInstance();
        return this.delayInstanceRepository.findAll(Example.of(delayInstance), PageRequest.of(0, size, Sort.by("id"))).toList();
    }

    @Override
    public Integer batchUpdateStatus(List<Long> ids, Integer status) {
        return this.delayInstanceRepository.batchUpdateStatus(ids, status, DateUtil.timestamp());
    }

    @Override
    public void deleteByTaskId(String taskId) {
        this.delayInstanceRepository.deleteByTaskId(taskId);
    }
}
