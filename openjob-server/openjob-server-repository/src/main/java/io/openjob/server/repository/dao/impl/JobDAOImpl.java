package io.openjob.server.repository.dao.impl;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.constant.TimeExpressionTypeEnum;
import io.openjob.common.util.DateUtil;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.util.SlotsUtil;
import io.openjob.server.repository.constant.JobStatusEnum;
import io.openjob.server.repository.dao.JobDAO;
import io.openjob.server.repository.dto.JobPageDTO;
import io.openjob.server.repository.entity.Job;
import io.openjob.server.repository.repository.JobRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Component
public class JobDAOImpl implements JobDAO {
    private final JobRepository jobRepository;

    @Autowired
    public JobDAOImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Long save(Job job) {
        Long timestamp = DateUtil.timestamp();
        job.setDeleted(CommonConstant.NO);
        job.setDeleteTime(0L);
        job.setCreateTime(timestamp);
        job.setUpdateTime(timestamp);

        //Save next execute time
        if (Objects.isNull(job.getNextExecuteTime())) {
            job.setNextExecuteTime(0L);
        }

        job.setSlotsId(SlotsUtil.getSlotsId(String.valueOf(timestamp)));
        job.setWorkflowId(0L);
        return jobRepository.save(job).getId();
    }

    @Override
    public Long updateNextExecuteTime(Long id, Long nextExecuteTime, Long updateTime) {
        this.jobRepository.findById(id)
                .ifPresent(j -> {
                    j.setNextExecuteTime(nextExecuteTime);
                    j.setUpdateTime(updateTime);
                    this.jobRepository.save(j);
                });
        return id;
    }

    @Override
    public Long update(Job job) {
        this.jobRepository.findById(job.getId())
                .ifPresent(j -> {
                    j.setNamespaceId(job.getNamespaceId());
                    j.setAppId(job.getAppId());
                    j.setName(job.getName());
                    j.setDescription(job.getDescription());
                    j.setProcessorInfo(job.getProcessorInfo());
                    j.setProcessorType(job.getProcessorType());
                    j.setExecuteType(job.getExecuteType());
                    j.setParams(job.getParams());
                    j.setParamsType(job.getParamsType());
                    j.setExtendParamsType(job.getExtendParamsType());
                    j.setExtendParams(job.getExtendParams());

                    // Update next execute time.
                    if (Objects.nonNull(job.getNextExecuteTime())){
                        j.setNextExecuteTime(job.getNextExecuteTime());
                    }

                    j.setFailRetryInterval(job.getFailRetryInterval());
                    j.setFailRetryTimes(job.getFailRetryTimes());
                    j.setConcurrency(job.getConcurrency());
                    j.setTimeExpression(job.getTimeExpression());
                    j.setTimeExpressionType(job.getTimeExpressionType());
                    j.setExecuteStrategy(job.getExecuteStrategy());
                    j.setUpdateTime(DateUtil.milliLongTime());
                    this.jobRepository.save(j);
                });
        return job.getId();
    }

    @Override
    public Long updateByStatusOrDeleted(Long id, Integer status, Integer deleted, Long nextExecuteTime) {
        this.jobRepository.findById(id)
                .ifPresent(j -> {
                    if (Objects.nonNull(status)) {
                        j.setStatus(status);
                    }
                    if (Objects.nonNull(deleted)) {
                        j.setDeleted(deleted);
                    }
                    if (Objects.nonNull(nextExecuteTime)) {
                        j.setNextExecuteTime(nextExecuteTime);
                    }
                    j.setUpdateTime(DateUtil.milliLongTime());
                    this.jobRepository.save(j);
                });
        return id;
    }

    @Override
    public Job getById(Long id) {
        return this.jobRepository.findById(id).orElse(null);
    }

    @Override
    public List<Job> listScheduledJobs(List<Long> slotIds, Long time) {
        List<String> notTypes = Arrays.asList(TimeExpressionTypeEnum.NONE.getType(), TimeExpressionTypeEnum.SECOND_DELAY.getType());
        return jobRepository.findBySlotsIdInAndStatusAndTimeExpressionTypeNotInAndNextExecuteTimeLessThanEqualAndDeleted(slotIds, JobStatusEnum.RUNNING.getStatus(), notTypes, time, CommonConstant.NO);
    }

    @Override
    public List<Job> listScheduledSecondJobs(List<Long> slotIds) {
        return null;
    }

    @Override
    public PageDTO<Job> pageList(JobPageDTO jobPageDTO) {
        // Matcher
        ExampleMatcher matching = ExampleMatcher.matching();
        Job job = new Job();
        job.setDeleted(CommonConstant.NO);

        if (StringUtils.isNotEmpty(jobPageDTO.getName())) {
            job.setName(jobPageDTO.getName());
            matching = matching.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }

        // Condition
        Example<Job> example = Example.of(job, matching);
        if (Objects.nonNull(jobPageDTO.getNamespaceId())) {
            job.setNamespaceId(jobPageDTO.getNamespaceId());
        }
        if (Objects.nonNull(jobPageDTO.getAppId())) {
            job.setAppId(jobPageDTO.getAppId());
        }
        if (Objects.nonNull(jobPageDTO.getStatus())) {
            job.setStatus(jobPageDTO.getStatus());
        }

        PageRequest pageRequest = PageRequest.of(jobPageDTO.getPage() - 1, jobPageDTO.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        // Pagination
        PageDTO<Job> pageDTO = new PageDTO<>();

        // Query
        Page<Job> pageList = this.jobRepository.findAll(example, pageRequest);
        if (!pageList.isEmpty()) {
            pageDTO.setPage(jobPageDTO.getPage());
            pageDTO.setSize(jobPageDTO.getSize());
            pageDTO.setTotal(pageList.getTotalElements());
            pageDTO.setList(pageList.toList());
        }
        return pageDTO;
    }
}
