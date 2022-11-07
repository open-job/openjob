package io.openjob.server.repository.data.impl;

import com.kezhilian.boot.common.util.BeanMapperUtil;
import com.kezhilian.boot.redis.operation.RedisOperation;
import io.openjob.server.repository.dao.JobAdminRuleDAO;
import io.openjob.server.repository.data.JobAdminRuleData;
import io.openjob.server.repository.dto.JobAdminRuleDTO;
import io.openjob.server.repository.entity.JobAdminRule;
import io.openjob.server.repository.util.CacheUtil;
// import com.kezhilian.wzl.service.order.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author inhere
 * @date 2022-11-07 13:43:07
 * @since 1.0.0
 */
@Component
public class JobAdminRuleDataImpl implements JobAdminRuleData {

    private final JobAdminRuleDAO jobAdminRuleDAO;
    private final RedisOperation redisOperation;

    @Autowired
    public JobAdminRuleDataImpl(JobAdminRuleDAO jobAdminRuleDAO, RedisOperation redisOperation) {
        this.jobAdminRuleDAO = jobAdminRuleDAO;
        this.redisOperation = redisOperation;
    }

    @Override
    public Long add(JobAdminRuleDTO dto) {
        JobAdminRule entity = BeanMapperUtil.map(dto, JobAdminRule.class);

        // 序列化扩展信息
        // if (Objects.nonNull(dto.getExtra())) {
        //    entity.setExtra(JsonUtil.toJsonFilterEmpty(dto.getExtra()));
        // }

        return jobAdminRuleDAO.add(entity);
    }

    @Override
    public Integer batchAdd(List<JobAdminRuleDTO> dtoList) {
        List<JobAdminRule> entityList = BeanMapperUtil.mapList(dtoList, JobAdminRuleDTO.class, JobAdminRule.class);

        return jobAdminRuleDAO.batchAdd(entityList);
    }

    @Override
    public JobAdminRuleDTO getById(Long id) {
        return BeanMapperUtil.map(jobAdminRuleDAO.getById(id), JobAdminRuleDTO.class);
    }

    @Override
    public JobAdminRuleDTO getByIdFromCache(Long id) {
        return redisOperation.string()
                .key(CacheKey.getJobAdminRuleByIdKey(id))
                .orElseGet(() -> getById(id));
    }

    @Override
    public Integer updateById(JobAdminRuleDTO dto) {
        JobAdminRule entity = BeanMapperUtil.map(dto, JobAdminRule.class);

        redisOperation.delete(CacheKey.getJobAdminRuleByIdKey(dto.getId()));

        return jobAdminRuleDAO.updateById(entity);
    }
}

