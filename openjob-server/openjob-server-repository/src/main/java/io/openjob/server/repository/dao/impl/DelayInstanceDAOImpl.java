package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.DelayInstanceDAO;
import io.openjob.server.repository.dto.DelayInstancePageDTO;
import io.openjob.server.repository.dto.DelayInstanceTotalDTO;
import io.openjob.server.repository.dto.GroupCountDTO;
import io.openjob.server.repository.entity.DelayInstance;
import io.openjob.server.repository.repository.DelayInstanceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.persistence.criteria.Predicate;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author stelin swoft@qq.com
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
                + "`create_time_date`, "
                + "`create_time_hour`, "
                + "`update_time`) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                ps.setInt(13, DateUtil.formatDateByTimestamp(d.getCreateTime()));
                ps.setInt(14, DateUtil.formatHourByTimestamp(d.getCreateTime()));
                ps.setLong(15, d.getUpdateTime());
            }

            @Override
            public int getBatchSize() {
                return delayInstanceList.size();
            }
        });

        return ints.length;
    }

    @Override
    public void updateDeleted(String taskid, Integer deleted) {
        Optional.ofNullable(this.delayInstanceRepository.findByTaskId(taskid))
                .ifPresent(d -> {
                    if (Objects.nonNull(deleted)) {
                        d.setDeleted(deleted);
                        d.setDeleteTime(DateUtil.timestamp());
                    }
                    this.delayInstanceRepository.save(d);
                });
    }

    @Override
    public DelayInstance getByTaskId(String taskId) {
        return this.delayInstanceRepository.findByTaskId(taskId);
    }

    @Override
    public List<DelayInstanceTotalDTO> getTopicTotalCount(List<String> topics, List<Integer> statuses) {
        return this.delayInstanceRepository.getDelayTotalCount(topics, statuses, CommonConstant.NO);
    }

    @Override
    public DelayInstance getFirstByDelayId(Long delayId) {
        return this.delayInstanceRepository.findFirstByDelayIdAndDeleted(delayId, CommonConstant.NO);
    }

    @Override
    public Integer updateStatus(String taskId, Integer status) {
        return this.delayInstanceRepository.updateStatusByTaskId(taskId, status);
    }

    @Override
    public Integer batchUpdateStatus(List<DelayInstance> updateList) {
        // When then sql.
        StringBuilder statusWhenThen = new StringBuilder();
        StringBuilder addressWhenThen = new StringBuilder();
        StringBuilder completeWhenThen = new StringBuilder();
        updateList.forEach(d -> {
            statusWhenThen.append(String.format(" when '%s' then %d ", d.getTaskId(), d.getStatus()));
            addressWhenThen.append(String.format(" when '%s' then '%s' ", d.getTaskId(), d.getWorkerAddress()));
            completeWhenThen.append(String.format(" when '%s' then '%s' ", d.getTaskId(), d.getCompleteTime()));
        });

        // Update sql.
        String sql = String.format("update `delay_instance` set `worker_address`=(case `task_id` %s ELSE `worker_address` END),"
                        + "`complete_time`=(case `task_id` %s ELSE `complete_time` END),`update_time`=%d, "
                        + "`status`=(case `task_id` %s ELSE `status` END) where `status`< (case `task_id` %s ELSE `status` END)",
                addressWhenThen,
                completeWhenThen,
                DateUtil.timestamp(),
                statusWhenThen,
                statusWhenThen);
        return this.jdbcTemplate.update(sql);
    }

    @Override
    public Integer deleteByTaskIds(List<String> taskIds) {
        return this.delayInstanceRepository.batchDeleteByTaskIds(taskIds, CommonConstant.YES, DateUtil.timestamp());
    }

    @Override
    public Long countTotalByNamespace(Long namespaceId) {
        return this.delayInstanceRepository.countByNamespaceIdAndDeleted(namespaceId, CommonConstant.NO);
    }

    @Override
    public Long deleteByCreateTimeAndNotStatus(Long lastTime, Integer status) {
        return this.delayInstanceRepository.deleteByCreateTimeLessThanEqualAndStatusNot(lastTime, status);
    }

    @Override
    public Long countTotalByNamespaceAndCreateTime(Long namespaceId, Long startTime, Long endTime, Integer status) {

        Specification<DelayInstance> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();

            // Deleted
            conditions.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), CommonConstant.NO));

            // Namespace id.
            conditions.add(criteriaBuilder.equal(root.get("namespaceId").as(Long.class), namespaceId));

            // Begin time
            conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Long.class), startTime));

            // End time
            conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Long.class), endTime));

            // Status.
            if (Objects.nonNull(status)) {
                conditions.add(criteriaBuilder.equal(root.get("status").as(Integer.class), status));
            }

            Predicate[] conditionAry = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(conditionAry));
        };

        return this.delayInstanceRepository.count(specification);
    }

    @Override
    public List<GroupCountDTO> countByNamespaceGroupByHourTime(Long namespaceId, Long startTime, Long endTime, Integer status) {
        return this.delayInstanceRepository.getDelayGroupByHour(namespaceId, startTime, endTime, status, CommonConstant.NO);
    }

    @Override
    public PageDTO<DelayInstance> pageList(DelayInstancePageDTO instancePageDTO) {

        Specification<DelayInstance> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();

            // Deleted
            conditions.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), CommonConstant.NO));

            // Namespace id.
            conditions.add(criteriaBuilder.equal(root.get("namespaceId").as(Long.class), instancePageDTO.getNamespaceId()));

            // Begin time
            if (Objects.nonNull(instancePageDTO.getBeginTime()) && instancePageDTO.getBeginTime() > 0) {
                conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Long.class), instancePageDTO.getBeginTime()));
            }

            // End time
            if (Objects.nonNull(instancePageDTO.getBeginTime()) && instancePageDTO.getBeginTime() > 0) {
                conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Long.class), instancePageDTO.getEndTime()));
            }

            // App id.
            if (Objects.nonNull(instancePageDTO.getAppId())) {
                conditions.add(criteriaBuilder.equal(root.get("appId").as(Long.class), instancePageDTO.getAppId()));
            }

            // Delay id.
            if (Objects.nonNull(instancePageDTO.getDelayId())) {
                conditions.add(criteriaBuilder.equal(root.get("delayId").as(String.class), instancePageDTO.getDelayId()));
            }

            // Task id.
            if (StringUtils.isNotEmpty(instancePageDTO.getTaskId())) {
                conditions.add(criteriaBuilder.equal(root.get("taskId").as(String.class), instancePageDTO.getTaskId()));
            }

            // Status.
            if (Objects.nonNull(instancePageDTO.getStatus())) {
                conditions.add(criteriaBuilder.equal(root.get("status").as(Integer.class), instancePageDTO.getStatus()));
            }

            Predicate[] conditionAry = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(conditionAry));
        };

        // Pagination
        PageDTO<DelayInstance> pageDTO = new PageDTO<>();
        PageRequest pageRequest = PageRequest.of(instancePageDTO.getPage() - 1, instancePageDTO.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        // Query
        Page<DelayInstance> pageList = this.delayInstanceRepository.findAll(specification, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(instancePageDTO.getPage());
            pageDTO.setSize(instancePageDTO.getSize());
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }
}
