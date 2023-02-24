package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.InstanceStatusEnum;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.repository.dao.JobInstanceDAO;
import io.openjob.server.repository.dto.JobInstancePageDTO;
import io.openjob.server.repository.entity.JobInstance;
import io.openjob.server.repository.repository.JobInstanceExecutorRepository;
import io.openjob.server.repository.repository.JobInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class JobInstanceDAOImpl implements JobInstanceDAO {
    private final JobInstanceRepository jobInstanceRepository;
    private final JobInstanceExecutorRepository jobInstanceExecutorRepository;

    @Autowired
    public JobInstanceDAOImpl(JobInstanceRepository jobInstanceRepository, JobInstanceExecutorRepository jobInstanceExecutorRepository) {
        this.jobInstanceRepository = jobInstanceRepository;
        this.jobInstanceExecutorRepository = jobInstanceExecutorRepository;
    }

    @Override
    public Long save(JobInstance jobInstance) {
        return this.jobInstanceRepository.save(jobInstance).getId();
    }

    @Override
    public Integer updateStatusById(Long id, Integer status) {
        long now = DateUtil.timestamp();
        return this.jobInstanceRepository.update(id, status, now, now);
    }

    @Override
    public Integer updateLastReportTimeByIds(List<Long> ids, Long lastReportTime) {
        return this.jobInstanceRepository.updateLastReportTimeByIds(ids, lastReportTime);
    }

    @Override
    public List<JobInstance> getUnDispatchedList(Set<Long> slotsIds, Long executeTime, InstanceStatusEnum status) {
        return this.jobInstanceRepository.findByExecuteTimeLessThanAndSlotsIdInAndStatus(executeTime, slotsIds, status.getStatus());
    }

    @Override
    public List<JobInstance> getFailoverList(Set<Long> slotsIds, Long lastReportTime, InstanceStatusEnum statusEnum) {
        return this.jobInstanceRepository.findByLastReportTimeLessThanAndSlotsIdInAndStatusAndTimeExpressionTypeNot(
                lastReportTime, slotsIds, statusEnum.getStatus(), TimeExpressionTypeEnum.ONE_TIME.name());
    }

    @Override
    public Integer updateByRunning(Long id, String workerAddress, InstanceStatusEnum instance, Long lastReportTime) {
        return this.jobInstanceRepository.updateByRunning(id, workerAddress, instance.getStatus(), lastReportTime);
    }

    @Override
    public JobInstance getOneByJobIdAndStatus(Long jobId, Long id, Integer status) {
        return this.jobInstanceRepository.findFirstByJobIdAndIdNotAndStatus(jobId, id, status);
    }

    @Override
    public PageDTO<JobInstance> pageList(JobInstancePageDTO instanceDTO) {
        Specification<JobInstance> specification = new Specification<JobInstance>() {
            @Override
            public Predicate toPredicate(Root<JobInstance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> conditions = new ArrayList<>();

                // App id.
                if (Objects.nonNull(instanceDTO.getAppId())) {
                    conditions.add(criteriaBuilder.equal(root.get("appId"), instanceDTO.getAppId()));
                }

                // Job id.
                if (Objects.nonNull(instanceDTO.getJobId())) {
                    conditions.add(criteriaBuilder.equal(root.get("jobId"), instanceDTO.getJobId()));
                }

                // Status
                if (Objects.nonNull(instanceDTO.getStatus())) {
                    conditions.add(criteriaBuilder.equal(root.get("status"), instanceDTO.getStatus()));
                }

                // Begin time
                if (Objects.nonNull(instanceDTO.getBeginTime())) {
                    conditions.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), instanceDTO.getBeginTime()));
                }

                // Begin time
                if (Objects.nonNull(instanceDTO.getEndTime())) {
                    conditions.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"), instanceDTO.getBeginTime()));
                }

                Predicate[] conditionAry = new Predicate[conditions.size()];
                return criteriaBuilder.and(conditions.toArray(conditionAry));
            }
        };

        // Pagination
        PageDTO<JobInstance> pageDTO = new PageDTO<>();
        PageRequest pageRequest = PageRequest.of(instanceDTO.getPage() - 1, instanceDTO.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        // Query
        Page<JobInstance> pageList = this.jobInstanceExecutorRepository.findAll(specification, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(instanceDTO.getPage());
            pageDTO.setSize(instanceDTO.getSize());
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }
}
