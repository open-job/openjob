package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dto.DelayInstancePageDTO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.repository.repository.DelayInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
    public Long updateDeleted(Long id, Integer deleted) {
        this.delayInstanceRepository.findById(id)
                .ifPresent(d -> {
                    if (Objects.nonNull(deleted)) {
                        d.setDeleted(deleted);
                        d.setDeleteTime(DateUtil.timestamp());
                    }
                    this.delayInstanceRepository.save(d);
                });
        return id;
    }

    @Override
    public PageDTO<DelayInstance> pageList(DelayInstancePageDTO instancePageDTO) {
        DelayInstance ds = new DelayInstance();
        ds.setDeleted(CommonConstant.NO);

        // Namespace
        if (Objects.nonNull(instancePageDTO.getNamespaceId())) {
            ds.setNamespaceId(instancePageDTO.getNamespaceId());
        }

        // Application
        if (Objects.nonNull(instancePageDTO.getAppId())) {
            ds.setAppId(instancePageDTO.getAppId());
        }

        // Topic
        if (Objects.nonNull(instancePageDTO.getTopic())) {
            ds.setTopic(instancePageDTO.getTopic());
        }

        // TaskId
        if (Objects.nonNull(instancePageDTO.getTaskId())) {
            ds.setTaskId(instancePageDTO.getTaskId());
        }

        // Condition
        Example<DelayInstance> example = Example.of(ds);
        PageRequest pageRequest = PageRequest.of(instancePageDTO.getPage() - 1, instancePageDTO.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        // Pagination
        PageDTO<DelayInstance> pageDTO = new PageDTO<>();

        // Query
        Page<DelayInstance> pageList = this.delayInstanceRepository.findAll(example, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(instancePageDTO.getPage());
            pageDTO.setSize(instancePageDTO.getSize());
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }

    @Override
    public List<DelayInstance> listDelayInstance(List<Long> slotIds, Integer time, Integer size) {
        DelayInstance delayInstance = new DelayInstance();
        return this.delayInstanceRepository.findAll(Example.of(delayInstance), PageRequest.of(0, size, Sort.by("id"))).toList();
    }

    @Override
    public Integer batchUpdateStatus(List<DelayInstance> updateList) {
        // When then sql.
        StringBuilder whenThen = new StringBuilder();
        updateList.forEach(d -> whenThen.append(String.format(" when '%s' then %d ", d.getTaskId(), d.getStatus())));

        // Update sql.
        String sql = String.format("update `delay_instance` set `update_time`=%d, `status`=(case `task_id` %s ELSE `status` END)", DateUtil.timestamp(), whenThen);
        return this.jdbcTemplate.update(sql);
    }

    @Override
    public Integer deleteByTaskIds(List<String> taskIds) {
        return this.delayInstanceRepository.batchDeleteByTaskIds(taskIds, CommonConstant.YES, DateUtil.timestamp());
    }
}
