package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dto.GroupCountDTO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.repository.JobInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class JobInstanceDAOImpl implements JobInstanceDAO {
    private final JobInstanceRepository jobInstanceRepository;

    @Autowired
    public JobInstanceDAOImpl(JobInstanceRepository jobInstanceRepository) {
        this.jobInstanceRepository = jobInstanceRepository;
    }

    @Override
    public Long save(JobInstance jobInstance) {
        jobInstance.setCreateTimeDate(DateUtil.formatDateByTimestamp(jobInstance.getCreateTime()));
        jobInstance.setCreateTimeHour(DateUtil.formatHourByTimestamp(jobInstance.getCreateTime()));
        return this.jobInstanceRepository.save(jobInstance).getId();
    }

    @Override
    public void deleteById(Long id) {
        this.jobInstanceRepository.deleteById(id);
    }

    @Override
    public Integer updateStatusById(Long id, Integer status, Integer failStatus) {
        long now = DateUtil.timestamp();
        return this.jobInstanceRepository.update(id, status, failStatus, now, now);
    }

    @Override
    public Integer updateLastReportTimeByIds(List<Long> ids, Long lastReportTime) {
        return this.jobInstanceRepository.updateLastReportTimeByIds(ids, lastReportTime);
    }

    @Override
    public JobInstance getById(Long id) {
        return this.jobInstanceRepository.findById(id).orElse(null);
    }

    @Override
    public List<JobInstance> getUnDispatchedList(Set<Long> slotsIds, Long executeTime, InstanceStatusEnum status) {
        return this.jobInstanceRepository.findByExecuteTimeLessThanAndSlotsIdInAndStatusAndDeleted(executeTime, slotsIds, status.getStatus(), CommonConstant.NO);
    }

    @Override
    public List<JobInstance> getFailoverList(Set<Long> slotsIds, Long lastReportTime, InstanceStatusEnum statusEnum) {
        return this.jobInstanceRepository.findByLastReportTimeLessThanAndSlotsIdInAndStatusAndTimeExpressionTypeNotAndDeleted(
                lastReportTime, slotsIds, statusEnum.getStatus(), TimeExpressionTypeEnum.ONE_TIME.name(), CommonConstant.NO);
    }

    @Override
    public Integer updateByRunning(Long id, String workerAddress, InstanceStatusEnum instance, Long lastReportTime) {
        return this.jobInstanceRepository.updateByRunning(id, workerAddress, instance.getStatus(), lastReportTime);
    }

    @Override
    public JobInstance getOneByJobIdAndStatus(Long jobId, Long id, List<Integer> statusList) {
        return this.jobInstanceRepository.findFirstByJobIdAndIdNotAndStatusInAndDeleted(jobId, id, statusList, CommonConstant.NO);
    }

    @Override
    public JobInstance getFirstByJobId(Long jobId) {
        return this.jobInstanceRepository.findFirstByJobIdAndDeleted(jobId, CommonConstant.NO);
    }

    @Override
    public Long countTotalByNamespace(Long namespaceId) {
        return this.jobInstanceRepository.countByNamespaceIdAndDeleted(namespaceId, CommonConstant.NO);
    }

    @Override
    public Long deleteByCreateTim(Long lastTime) {
        return this.jobInstanceRepository.deleteByCreateTimeLessThanEqual(lastTime);
    }

    @Override
    public Long countTotalByNamespaceAndCreateTime(Long namespaceId, Long startTime, Long endTime, Integer status) {
        Specification<JobInstance> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();

            // Deleted
            conditions.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), CommonConstant.NO));

            // Status
            if (Objects.nonNull(status)) {
                conditions.add(criteriaBuilder.equal(root.get("status").as(Integer.class), status));
            }

            // Namespace id.
            conditions.add(criteriaBuilder.equal(root.get("namespaceId").as(Long.class), namespaceId));

            // Begin time
            conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Long.class), startTime));

            // End time
            conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Long.class), endTime));

            Predicate[] conditionAry = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(conditionAry));
        };
        return this.jobInstanceRepository.count(specification);
    }

    @Override
    public List<GroupCountDTO> countByNamespaceGroupByHourTime(Long namespaceId, Long startTime, Long endTime, Integer status) {
        return this.jobInstanceRepository.getJobInstanceGroupByHour(namespaceId, startTime, endTime, status, CommonConstant.NO);
    }

    @Override
    public List<GroupCountDTO> countByNamespaceGroupByDateTime(Long namespaceId, Long startTime, Long endTime, Integer status) {
        return this.jobInstanceRepository.getJobInstanceGroupByDate(namespaceId, startTime, endTime, status, CommonConstant.NO);
    }

    @Override
    public List<GroupCountDTO> countByNamespaceGroupByStatus(Long namespaceId, Long startTime, Long endTime) {
        return this.jobInstanceRepository.getJobInstanceGroupStatus(namespaceId, startTime, endTime, CommonConstant.NO);
    }

    @Override
    public PageDTO<JobInstance> pageList(JobInstancePageDTO instanceDTO) {
        Specification<JobInstance> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();

            // Deleted
            conditions.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), CommonConstant.NO));

            // Namespace id.
            conditions.add(criteriaBuilder.equal(root.get("namespaceId").as(Long.class), instanceDTO.getNamespaceId()));

            // ID
            if (Objects.nonNull(instanceDTO.getId())) {
                conditions.add(criteriaBuilder.equal(root.get("id").as(Long.class), instanceDTO.getId()));
            }

            // App id.
            if (Objects.nonNull(instanceDTO.getAppId())) {
                conditions.add(criteriaBuilder.equal(root.get("appId").as(Long.class), instanceDTO.getAppId()));
            }

            // Job id.
            if (Objects.nonNull(instanceDTO.getJobId())) {
                conditions.add(criteriaBuilder.equal(root.get("jobId").as(Long.class), instanceDTO.getJobId()));
            }

            // Status
            if (Objects.nonNull(instanceDTO.getStatus())) {
                conditions.add(criteriaBuilder.equal(root.get("status").as(Integer.class), instanceDTO.getStatus()));
            }

            // Begin time
            if (Objects.nonNull(instanceDTO.getBeginTime()) && instanceDTO.getBeginTime() > 0) {
                conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Long.class), instanceDTO.getBeginTime()));
            }

            // Begin time
            if (Objects.nonNull(instanceDTO.getEndTime()) && instanceDTO.getEndTime() > 0) {
                conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Long.class), instanceDTO.getEndTime()));
            }

            Predicate[] conditionAry = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(conditionAry));
        };

        // Pagination
        PageDTO<JobInstance> pageDTO = new PageDTO<>();
        PageRequest pageRequest = PageRequest.of(instanceDTO.getPage() - 1, instanceDTO.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        // Query
        Page<JobInstance> pageList = this.jobInstanceRepository.findAll(specification, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(instanceDTO.getPage());
            pageDTO.setSize(instanceDTO.getSize());
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }
}
